package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.CompetitorIndustry;
import fr.ippon.kompetitors.repository.CompetitorIndustryRepository;
import fr.ippon.kompetitors.service.dto.CompetitorIndustryDTO;
import fr.ippon.kompetitors.service.mapper.CompetitorIndustryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompetitorIndustry}.
 */
@Service
@Transactional
public class CompetitorIndustryService {

    private final Logger log = LoggerFactory.getLogger(CompetitorIndustryService.class);

    private final CompetitorIndustryRepository competitorIndustryRepository;

    private final CompetitorIndustryMapper competitorIndustryMapper;

    public CompetitorIndustryService(CompetitorIndustryRepository competitorIndustryRepository, CompetitorIndustryMapper competitorIndustryMapper) {
        this.competitorIndustryRepository = competitorIndustryRepository;
        this.competitorIndustryMapper = competitorIndustryMapper;
    }

    /**
     * Save a competitorIndustry.
     *
     * @param competitorIndustryDTO the entity to save.
     * @return the persisted entity.
     */
    public CompetitorIndustryDTO save(CompetitorIndustryDTO competitorIndustryDTO) {
        log.debug("Request to save CompetitorIndustry : {}", competitorIndustryDTO);
        CompetitorIndustry competitorIndustry = competitorIndustryMapper.toEntity(competitorIndustryDTO);
        competitorIndustry = competitorIndustryRepository.save(competitorIndustry);
        return competitorIndustryMapper.toDto(competitorIndustry);
    }

    /**
     * Get all the competitorIndustries.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompetitorIndustryDTO> findAll() {
        log.debug("Request to get all CompetitorIndustries");
        return competitorIndustryRepository.findAll().stream()
            .map(competitorIndustryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one competitorIndustry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompetitorIndustryDTO> findOne(Long id) {
        log.debug("Request to get CompetitorIndustry : {}", id);
        return competitorIndustryRepository.findById(id)
            .map(competitorIndustryMapper::toDto);
    }

    /**
     * Delete the competitorIndustry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompetitorIndustry : {}", id);
        competitorIndustryRepository.deleteById(id);
    }

    /**
     * Get all the competitorIndustries by competitor id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompetitorIndustryDTO> findAllByCompetitorId(Long id) {
        log.debug("Request to get all CompetitorIndustries by competitor id");
        return competitorIndustryRepository.findAllByCompetitorId(id).stream()
            .map(competitorIndustryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
