package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.DashboardFinance;
import fr.ippon.kompetitors.repository.DashboardFinanceRepository;
import fr.ippon.kompetitors.service.dto.DashboardFinanceDTO;
import fr.ippon.kompetitors.service.mapper.DashboardFinanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DashboardFinance}.
 */
@Service
@Transactional
public class DashboardFinanceService {

    private final Logger log = LoggerFactory.getLogger(DashboardFinanceService.class);

    @Value("${annual-account-statistics.last-year}")
    private Integer statisticsLastYear;

    private final DashboardFinanceRepository dashboardFinanceRepository;
    private final DashboardFinancePrepareService dashboardFinancePrepareService;

    private final DashboardFinanceMapper dashboardFinanceMapper;

    public DashboardFinanceService(DashboardFinanceRepository dashboardFinanceRepository, DashboardFinanceMapper dashboardFinanceMapper,
                                   DashboardFinancePrepareService dashboardFinancePrepareService) {
        this.dashboardFinanceRepository = dashboardFinanceRepository;
        this.dashboardFinanceMapper = dashboardFinanceMapper;
        this.dashboardFinancePrepareService = dashboardFinancePrepareService;
    }

    /**
     * Save a dashboardFinance.
     *
     * @param dashboardFinanceDTO the entity to save.
     * @return the persisted entity.
     */
    public DashboardFinanceDTO save(DashboardFinanceDTO dashboardFinanceDTO) {
        log.debug("Request to save DashboardFinance : {}", dashboardFinanceDTO);
        DashboardFinance dashboardFinance = dashboardFinanceMapper.toEntity(dashboardFinanceDTO);
        dashboardFinance = dashboardFinanceRepository.save(dashboardFinance);
        return dashboardFinanceMapper.toDto(dashboardFinance);
    }

    /**
     * Get all the dashboardFinances.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DashboardFinanceDTO> findAll() {
        log.debug("Request to get all DashboardFinances");
        return dashboardFinanceRepository.findAll().stream()
            .map(dashboardFinanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dashboardFinance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DashboardFinanceDTO> findOne(Long id) {
        log.debug("Request to get DashboardFinance : {}", id);
        return dashboardFinanceRepository.findById(id)
            .map(dashboardFinanceMapper::toDto);
    }

    /**
     * Delete the dashboardFinance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DashboardFinance : {}", id);
        dashboardFinanceRepository.deleteById(id);
    }

    /**
     * Get all the dashboardFinances for year.
     *
     * @return the list of entities.
     */
    public List<DashboardFinanceDTO> findAllForYear(Integer year) {
        log.debug("Request to get all DashboardFinances for year {}", year);
        List<DashboardFinance> result = dashboardFinanceRepository.findAllByYear(year);
        if (result.size() == 0) {
            return dashboardFinancePrepareService.prepareDashboardForYear(year, false);
        }
        return result.stream()
            .map(dashboardFinanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the dashboardFinances for competitor for last available year.
     *
     * @return the list of entities.
     */
    public List<DashboardFinanceDTO> findAllLastAvailableYear(Long competitorId) {
        log.debug("Request to get all DashboardFinances for last available year by competitor id {}", competitorId);
        int year = LocalDateTime.now().getYear() - 1 >= statisticsLastYear ? LocalDateTime.now().getYear() - 1 : statisticsLastYear;
        List<DashboardFinanceDTO> result = new ArrayList<>();
        for (int i = year; i >= statisticsLastYear; i--) {
            result = findAllForYear(i);
            Optional<DashboardFinanceDTO> referenceStat = result.stream()
                .filter(dto -> dto.getCompetitorId().equals(competitorId))
                .findAny();
            if (referenceStat != null && referenceStat.isPresent()) {
                if ( referenceStat.get().getGrossSales() != null && referenceStat.get().getGrossSales() != 0) {
                    break;
                }
            }
        }
        return result;
    }
}
