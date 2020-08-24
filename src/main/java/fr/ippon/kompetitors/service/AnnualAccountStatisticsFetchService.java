package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.AnnualAccount;
import fr.ippon.kompetitors.domain.AnnualAccountStatistics;
import fr.ippon.kompetitors.domain.Legal;
import fr.ippon.kompetitors.repository.AnnualAccountRepository;
import fr.ippon.kompetitors.repository.AnnualAccountStatisticsRepository;
import fr.ippon.kompetitors.repository.LegalRepository;
import fr.ippon.kompetitors.service.dto.AnnualAccountResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnnualAccountStatisticsFetchService {

    @Value("${annual-account-statistics.enabled}")
    private Boolean statisticsEnabled;

    @Value("${annual-account-statistics.last-year}")
    private Integer statisticsLastYear;

    @Value("${annual-account-statistics.token}")
    private String apiToken;

    private final String API = "https://api.datainfogreffe.fr/api/v1/Entreprise/ComptesAnnuels/";

    private final Logger log = LoggerFactory.getLogger(AnnualAccountStatisticsFetchService.class);

    private final AnnualAccountRepository annualAccountRepository;
    private final AnnualAccountStatisticsRepository annualAccountStatisticsRepository;
    private final LegalRepository legalRepository;

    public AnnualAccountStatisticsFetchService(AnnualAccountRepository annualAccountRepository,
                                               AnnualAccountStatisticsRepository annualAccountStatisticsRepository,
                                               LegalRepository legalRepository) {
        this.annualAccountRepository = annualAccountRepository;
        this.annualAccountStatisticsRepository = annualAccountStatisticsRepository;
        this.legalRepository = legalRepository;
    }

    private String getUrl(String siren, int year) {
        return String.format("%s%s?millesime=%d&token=%s", API, siren, year, apiToken);
    }

    @Scheduled(cron="0 0 0 ? * MON")
    public void fetchAnnualAccountStatistics() {
        log.info("ANNUAL STATISTICS UPDATE feature is enabled : {}", statisticsEnabled);
        if (!statisticsEnabled) return;

        Set<AnnualAccountStatistics> renewStatistics = new HashSet<>();
        Set<Integer> statYears = getStatisticYears();
        for (Legal legal : legalRepository.findAll()) {
            renewStatistics.addAll(getStaleStatistics(legal.getSiren(), statYears));
        }
        renewStatistics.forEach(this::updateAnnualAccountStatistics);
    }

    public void fetchAnnualAccountStatisticsBySiren(String siren) {
        log.info("ANNUAL STATISTICS UPDATE feature is enabled : {}", statisticsEnabled);
        if (!statisticsEnabled) return;

        Set<Integer> statYears = getStatisticYears();
        List<AnnualAccountStatistics> renewStatistics = getStaleStatistics(siren, statYears);
        renewStatistics.forEach(this::updateAnnualAccountStatistics);
    }

    private void updateAnnualAccountStatistics(AnnualAccountStatistics statistic) {
        log.info("Updating Annual account statistics for siren {}, year {}", statistic.getSiren(), statistic.getYear());
//        String url = getUrl("552111111", 2014);
        String url = getUrl(statistic.getSiren(), statistic.getYear());
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(10000);
        restTemplate.setRequestFactory(requestFactory);

        try {
            ResponseEntity<AnnualAccountResponse> response = restTemplate.getForEntity(url, AnnualAccountResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getData().getPostes() != null) {
                List<AnnualAccount> accounts =  response
                    .getBody()
                    .getData()
                    .getPostes()
                    .stream()
                    .map(poste -> new AnnualAccount(
                        statistic.getSiren(),
                        statistic.getYear(),
                        poste.getCodeInfogreffe(),
                        Long.parseLong(poste.getValeur())
                    )).collect(Collectors.toList());
                saveOrUpdateStatistics(statistic, accounts);
                log.info("Updated Annual account statistics for siren {}, year {}", statistic.getSiren(), statistic.getYear());
                updateStatisticStatus(statistic, response.getStatusCodeValue(), response.getBody().getMetadata().toString());
            } else {
                log.info("Failed to updated Annual account statistics for siren {}, year {}.\r\n Code: {}, message {}",
                    statistic.getSiren(), statistic.getYear(), response.getStatusCode(), response.toString());
                updateStatisticStatus(statistic, response.getStatusCodeValue(), response.toString());
            }
        } catch (HttpClientErrorException e) {
            updateStatisticStatus(statistic, e.getRawStatusCode(), e.getResponseBodyAsString());
            log.error("HTTP error occurred while updating Annual account statistics for siren {}, year {}", statistic.getSiren(), statistic.getYear(), e);
        } catch (Exception e) {
            updateStatisticStatus(statistic, 500, e.getMessage());
            log.error("Error occurred while updating Annual account statistics for siren {}, year {}", statistic.getSiren(), statistic.getYear(), e);
        }
    }

    private void saveOrUpdateStatistics(AnnualAccountStatistics statistic, List<AnnualAccount> accounts) {
        List<AnnualAccount> existing = annualAccountRepository.getAllBySirenAndYear(statistic.getSiren(), statistic.getYear());
        if (existing.size() > 0) {
            List<AnnualAccount> modified = new ArrayList<>();
            existing.forEach(existingAccount -> {
                    if (!accounts.contains(existingAccount)) {
                        Optional<AnnualAccount> optModifiedAccount = accounts
                            .stream()
                            .filter(e -> e.getCode().equals(existingAccount.getCode())).findFirst();
                        optModifiedAccount.ifPresent(mod -> {
                            existingAccount.setValue(mod.getValue());
                            modified.add(existingAccount);
                        });
                    }
                }
            );
            annualAccountRepository.saveAll(modified);
        } else {
            annualAccountRepository.saveAll(accounts);
        }
    }

    private void updateStatisticStatus(AnnualAccountStatistics statistic, Integer code, String message) {
        statistic.setCode(code);
        statistic.setMessage(message);
        statistic.setModified(Instant.now());
        annualAccountStatisticsRepository.save(statistic);
    }

    private Set<Integer> getStatisticYears() {
        Set<Integer> result = new HashSet<>();
        if (statisticsLastYear >= LocalDateTime.now().getYear()) return result;

        for (int year = statisticsLastYear; year < LocalDateTime.now().getYear(); year++) {
            result.add(year);
        }
        return result;
    }

    private List<AnnualAccountStatistics> getStaleStatistics(String siren, Set<Integer> years) {
        Set<Integer> staleYears = new HashSet<>(years);
        List<AnnualAccountStatistics> result = new ArrayList<>();
        if (siren == null || "".equals(siren)) return result;
        for (AnnualAccountStatistics statistic : annualAccountStatisticsRepository.findAllBySiren(siren)) {
            if (staleYears.contains(statistic.getYear())
                && (statistic.getCode() == null
                || (statistic.getCode() > 200 && statistic.getCode() < 404))) {
                result.add(statistic);
            }
            staleYears.remove(statistic.getYear());
        }
        staleYears.forEach(year -> result.add(new AnnualAccountStatistics(siren, year)));
        return result;
    }
}
