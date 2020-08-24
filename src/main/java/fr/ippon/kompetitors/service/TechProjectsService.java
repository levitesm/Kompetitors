package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.TechProjects;
import fr.ippon.kompetitors.repository.TechProjectsRepository;
import fr.ippon.kompetitors.service.dto.TechProjectsDTO;
import fr.ippon.kompetitors.service.mapper.TechProjectsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TechProjects}.
 */
@Service
@Transactional
public class TechProjectsService {

    private final Logger log = LoggerFactory.getLogger(TechProjectsService.class);

    private final TechProjectsRepository techProjectsRepository;

    private final TechProjectsMapper techProjectsMapper;

    public TechProjectsService(TechProjectsRepository techProjectsRepository, TechProjectsMapper techProjectsMapper) {
        this.techProjectsRepository = techProjectsRepository;
        this.techProjectsMapper = techProjectsMapper;
    }

    /**
     * Save a techProjects.
     *
     * @param techProjectsDTO the entity to save.
     * @return the persisted entity.
     */
    public TechProjectsDTO save(TechProjectsDTO techProjectsDTO) {
        log.debug("Request to save TechProjects : {}", techProjectsDTO);
        TechProjects techProjects = techProjectsMapper.toEntity(techProjectsDTO);
        techProjects = techProjectsRepository.save(techProjects);
        return techProjectsMapper.toDto(techProjects);
    }

    /**
     * Get all the techProjects.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechProjectsDTO> findAll() {
        log.debug("Request to get all TechProjects");
        return techProjectsRepository.findAll().stream()
            .map(techProjectsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one techProjects by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechProjectsDTO> findOne(Long id) {
        log.debug("Request to get TechProjects : {}", id);
        return techProjectsRepository.findById(id)
            .map(techProjectsMapper::toDto);
    }

    /**
     * Delete the techProjects by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechProjects : {}", id);
        techProjectsRepository.deleteById(id);
    }

    /**
     * Get all the techProjects by competitor id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechProjectsDTO> findAllByCompetitorId(Long id) {
        log.debug("Request to get all techProjects by competitor id {}", id);
        return techProjectsRepository.findAllByCompetitorId(id).stream()
            .map(techProjectsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
