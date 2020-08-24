package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.AnnualAccountStatistics;
import fr.ippon.kompetitors.repository.AnnualAccountStatisticsRepository;
import fr.ippon.kompetitors.service.dto.AnnualAccountStatisticsDTO;
import fr.ippon.kompetitors.service.mapper.AnnualAccountStatisticsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnnualAccountStatistics}.
 */
@Service
@Transactional
public class AnnualAccountStatisticsService {

    private final Logger log = LoggerFactory.getLogger(AnnualAccountStatisticsService.class);

    private final AnnualAccountStatisticsRepository annualAccountStatisticsRepository;

    private final AnnualAccountStatisticsMapper annualAccountStatisticsMapper;

    public AnnualAccountStatisticsService(AnnualAccountStatisticsRepository annualAccountStatisticsRepository, AnnualAccountStatisticsMapper annualAccountStatisticsMapper) {
        this.annualAccountStatisticsRepository = annualAccountStatisticsRepository;
        this.annualAccountStatisticsMapper = annualAccountStatisticsMapper;
    }

    /**
     * Save a annualAccountStatistics.
     *
     * @param annualAccountStatisticsDTO the entity to save.
     * @return the persisted entity.
     */
    public AnnualAccountStatisticsDTO save(AnnualAccountStatisticsDTO annualAccountStatisticsDTO) {
        log.debug("Request to save AnnualAccountStatistics : {}", annualAccountStatisticsDTO);
        AnnualAccountStatistics annualAccountStatistics = annualAccountStatisticsMapper.toEntity(annualAccountStatisticsDTO);
        annualAccountStatistics = annualAccountStatisticsRepository.save(annualAccountStatistics);
        return annualAccountStatisticsMapper.toDto(annualAccountStatistics);
    }

    /**
     * Get all the annualAccountStatistics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnualAccountStatisticsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnnualAccountStatistics");
        return annualAccountStatisticsRepository.findAll(pageable)
            .map(annualAccountStatisticsMapper::toDto);
    }


    /**
     * Get one annualAccountStatistics by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnnualAccountStatisticsDTO> findOne(Long id) {
        log.debug("Request to get AnnualAccountStatistics : {}", id);
        return annualAccountStatisticsRepository.findById(id)
            .map(annualAccountStatisticsMapper::toDto);
    }

    /**
     * Delete the annualAccountStatistics by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnnualAccountStatistics : {}", id);
        annualAccountStatisticsRepository.deleteById(id);
    }
}
