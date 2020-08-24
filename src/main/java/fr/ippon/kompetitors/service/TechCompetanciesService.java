package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.TechCompetancies;
import fr.ippon.kompetitors.repository.TechCompetanciesRepository;
import fr.ippon.kompetitors.service.dto.TechCompetanciesDTO;
import fr.ippon.kompetitors.service.mapper.TechCompetanciesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TechCompetancies}.
 */
@Service
@Transactional
public class TechCompetanciesService {

    private final Logger log = LoggerFactory.getLogger(TechCompetanciesService.class);

    private final TechCompetanciesRepository techCompetanciesRepository;

    private final TechCompetanciesMapper techCompetanciesMapper;

    public TechCompetanciesService(TechCompetanciesRepository techCompetanciesRepository, TechCompetanciesMapper techCompetanciesMapper) {
        this.techCompetanciesRepository = techCompetanciesRepository;
        this.techCompetanciesMapper = techCompetanciesMapper;
    }

    /**
     * Save a techCompetancies.
     *
     * @param techCompetanciesDTO the entity to save.
     * @return the persisted entity.
     */
    public TechCompetanciesDTO save(TechCompetanciesDTO techCompetanciesDTO) {
        log.debug("Request to save TechCompetancies : {}", techCompetanciesDTO);
        TechCompetancies techCompetancies = techCompetanciesMapper.toEntity(techCompetanciesDTO);
        techCompetancies = techCompetanciesRepository.save(techCompetancies);
        return techCompetanciesMapper.toDto(techCompetancies);
    }

    /**
     * Get all the techCompetancies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechCompetanciesDTO> findAll() {
        log.debug("Request to get all TechCompetancies");
        return techCompetanciesRepository.findAll().stream()
            .map(techCompetanciesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one techCompetancies by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechCompetanciesDTO> findOne(Long id) {
        log.debug("Request to get TechCompetancies : {}", id);
        return techCompetanciesRepository.findById(id)
            .map(techCompetanciesMapper::toDto);
    }

    /**
     * Delete the techCompetancies by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechCompetancies : {}", id);
        techCompetanciesRepository.deleteById(id);
    }

    /**
     * Get all the techCompetancies by competitor id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechCompetanciesDTO> findAllByCompetitorId(Long id) {
        log.debug("Request to get all TechCompetancies by competitor id {}", id);
        return techCompetanciesRepository.findAllByCompetitorId(id).stream()
            .map(techCompetanciesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
