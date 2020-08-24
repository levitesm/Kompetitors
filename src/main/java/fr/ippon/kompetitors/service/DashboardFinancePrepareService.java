package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.AnnualAccount;
import fr.ippon.kompetitors.domain.Competitors;
import fr.ippon.kompetitors.domain.DashboardFinance;
import fr.ippon.kompetitors.domain.Legal;
import fr.ippon.kompetitors.repository.AnnualAccountRepository;
import fr.ippon.kompetitors.repository.DashboardFinanceRepository;
import fr.ippon.kompetitors.repository.LegalRepository;
import fr.ippon.kompetitors.service.dto.DashboardFinanceDTO;
import fr.ippon.kompetitors.service.mapper.DashboardFinanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DashboardFinancePrepareService {

    private final Logger log = LoggerFactory.getLogger(DashboardFinancePrepareService.class);

    @Value("${annual-account-statistics.last-year}")
    private Integer statisticsLastYear;

    private final LegalRepository legalRepository;
    private final DashboardFinanceRepository dashboardFinanceRepository;
    private final AnnualAccountRepository annualAccountRepository;
    private final DashboardFinanceMapper dashboardFinanceMapper;

    public DashboardFinancePrepareService(LegalRepository legalRepository, DashboardFinanceRepository dashboardFinanceRepository,
                                          AnnualAccountRepository annualAccountRepository, DashboardFinanceMapper dashboardFinanceMapper) {
        this.legalRepository = legalRepository;
        this.dashboardFinanceRepository = dashboardFinanceRepository;
        this.annualAccountRepository = annualAccountRepository;
        this.dashboardFinanceMapper = dashboardFinanceMapper;
    }

    @Scheduled(cron = "0 5 0 ? * MON")
    public void updateDashboardPreviousYear() {
        log.info("Weekly update of DashboardFinance initiated");
        prepareDashboardForYear(LocalDateTime.now().getYear() - 1, true);
    }

    @Transactional
    public List<DashboardFinanceDTO> prepareDashboardForYear(Integer year, Boolean update) {
        log.info("Enter prepareDashboardForYear. Year: {}, Update: {}", year, update);
        if (year < statisticsLastYear || year > LocalDateTime.now().getYear()) return new ArrayList<>();
        Map<Long, DashboardFinance> dashboardMap = dashboardFinanceRepository.findAllByYear(year).stream()
            .collect(Collectors.toMap(DashboardFinance::getCompetitorId, Function.identity()));
        List<DashboardFinance> dashboards = new ArrayList<>();
        for (Legal legal : legalRepository.findAll()) {
            if (legal.getCompetitorId() == null || legal.getSiren() == null) continue;
            if (!dashboardMap.containsKey(legal.getCompetitorId())) {
                dashboards.add(prepareCompetitorDashboard(legal.getCompetitor(), legal.getSiren(), year));
            } else if (update) {
                DashboardFinance updated = prepareCompetitorDashboard(legal.getCompetitor(), legal.getSiren(), year);
                updated.setId(dashboardMap.get(legal.getCompetitorId()).getId());
                dashboards.add(updated);
                log.info("Updating dashboardFinance: {}", updated);
            }
        }
        dashboardFinanceRepository.saveAll(dashboards);
        return dashboards.stream()
            .map(dashboardFinanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private DashboardFinance prepareCompetitorDashboard(Competitors competitor, String siren, Integer year) {
        DashboardFinance result = new DashboardFinance();
        result.setCompetitor(competitor);
        result.setYear(year);
        Map<String, Long> statistics = annualAccountRepository.getAllBySirenAndYear(siren, year).stream()
            .collect(Collectors.toMap(AnnualAccount::getCode, AnnualAccount::getValue));

        Map<String, Long> statisticsPrev = annualAccountRepository.getAllBySirenAndYear(siren, year-1).stream()
            .collect(Collectors.toMap(AnnualAccount::getCode, AnnualAccount::getValue));

        result.setGrossSales(getGrossSales(statistics));
        result.setGrossSalesGrowth((getGrossSales(statistics)!=null && getGrossSales(statisticsPrev)!=null)?((getGrossSales(statistics)-getGrossSales(statisticsPrev))*100.0/getGrossSales(statisticsPrev)):null);
        result.setGrossSalesPerEmployee(getGrossSalesPerEmployee(statistics));
        result.setEbit(getEbit(statistics));
        result.setNetResult(getNetResult(statistics));
        result.setNetResultGrowth((getNetResult(statistics)!=null && getNetResult(statisticsPrev)!=null)?((getNetResult(statistics)-getNetResult(statisticsPrev))*100.0/getNetResult(statisticsPrev)):null);
        result.setWorkforce(getWorkForce(statistics));
        result.setGrossSalesPerConsultant(getGrossSalesPerConsultant(statistics));
        result.setAveragePay(getAveragePay(statistics));
        result.setNetResultPercent(getNetResultPercent(statistics));
        log.info("Generated new dashboardFinance: {}", result);
        return result;
    }

    private Long getGrossSales(Map<String, Long> statistics) {
        if (statistics.containsKey("N40")) return statistics.get("N40");
        if (statistics.containsKey("CFL")) return statistics.get("CFL");
        return null;
    }

    private Long getWorkForce(Map<String, Long> statistics) {
        if (statistics.containsKey("876")) return statistics.get("876");
        if (statistics.containsKey("MYP")) return statistics.get("MYP");
        return null;
    }

    private Long getGrossSalesPerEmployee(Map<String, Long> statistics) {
        Long gross = getGrossSales(statistics);
        Long workForce = getWorkForce(statistics);
        return (gross != null && workForce != null && workForce != 0) ? gross / workForce : null;
    }

    private Long getEbit(Map<String, Long> statistics) {
        Long oper = statistics.getOrDefault("CGG", 0L);
        return oper;
    }

    private Long getNetResult(Map<String, Long> statistics) {
        if (statistics.containsKey("P8")) return statistics.get("P8");
        if (statistics.containsKey("R4")) return statistics.get("R4");
        if (statistics.containsKey("136")) return statistics.get("136");
        if (statistics.containsKey("DI")) return statistics.get("DI");
        if (statistics.containsKey("BDI")) return statistics.get("BDI");
        if (statistics.containsKey("HN")) return statistics.get("HN");
        if (statistics.containsKey("DHN")) return statistics.get("DHN");
        return null;
    }

    private Long getGrossSalesPerConsultant(Map<String, Long> statistics) {
        Long gross = getGrossSales(statistics);
        Long sub = getSubcontract(statistics);
        Long workForce = getWorkForce(statistics);
        return (gross != null && sub != null && workForce != null && workForce != 0) ? (gross - sub) / workForce : null;
    }

    private Long getSubcontract(Map<String, Long> statistics) {
        if (statistics.containsKey("YT")) return statistics.get("YT");
        if (statistics.containsKey("MYT")) return statistics.get("MYT");
        return null;
    }

    private Long getAveragePay(Map<String, Long> statistics) {
        Long payroll = getPayroll(statistics);
        Long workForce = getWorkForce(statistics);
        return (payroll != null && workForce != null && workForce != 0) ? payroll / workForce : null;
    }

    private Long getPayroll(Map<String, Long> statistics) {
        if (statistics.containsKey("N47") || statistics.containsKey("N48")) {
            return (statistics.containsKey("N47") ? statistics.get("N47") : 0) +
                (statistics.containsKey("N48") ? statistics.get("N48") : 0);
        }
        if (statistics.containsKey("CFY") || statistics.containsKey("CFZ")) {
            return (statistics.containsKey("CFY") ? statistics.get("CFY") : 0) +
                (statistics.containsKey("CFZ") ? statistics.get("CFZ") : 0);
        }
        return null;
    }

    private Integer getNetResultPercent(Map<String, Long> statistics) {
        Long netResult = getNetResult(statistics);
        Long grossSales = getGrossSales(statistics);
        if (netResult == null || grossSales == null) {
            return null;
        }
        Integer result = null;
        if (grossSales != null && netResult != null && grossSales != 0) {
            try {
                result = Math.toIntExact((netResult * 100) / grossSales);
            } catch (ArithmeticException e) {
                log.error("Failed to generate NetResult percent {} / {}", netResult, grossSales, e);
            }
        }
        return result;
    }
}
