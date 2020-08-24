package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.TechTools;
import fr.ippon.kompetitors.repository.TechToolsRepository;
import fr.ippon.kompetitors.service.dto.TechToolsDTO;
import fr.ippon.kompetitors.service.mapper.TechToolsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TechTools}.
 */
@Service
@Transactional
public class TechToolsService {

    private final Logger log = LoggerFactory.getLogger(TechToolsService.class);

    private final TechToolsRepository techToolsRepository;

    private final TechToolsMapper techToolsMapper;

    public TechToolsService(TechToolsRepository techToolsRepository, TechToolsMapper techToolsMapper) {
        this.techToolsRepository = techToolsRepository;
        this.techToolsMapper = techToolsMapper;
    }

    /**
     * Save a techTools.
     *
     * @param techToolsDTO the entity to save.
     * @return the persisted entity.
     */
    public TechToolsDTO save(TechToolsDTO techToolsDTO) {
        log.debug("Request to save TechTools : {}", techToolsDTO);
        TechTools techTools = techToolsMapper.toEntity(techToolsDTO);
        techTools = techToolsRepository.save(techTools);
        return techToolsMapper.toDto(techTools);
    }

    /**
     * Get all the techTools.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechToolsDTO> findAll() {
        log.debug("Request to get all TechTools");
        return techToolsRepository.findAll().stream()
            .map(techToolsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one techTools by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechToolsDTO> findOne(Long id) {
        log.debug("Request to get TechTools : {}", id);
        return techToolsRepository.findById(id)
            .map(techToolsMapper::toDto);
    }

    /**
     * Delete the techTools by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechTools : {}", id);
        techToolsRepository.deleteById(id);
    }

    /**
     * Get all the techTools by competitor id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechToolsDTO> findAllByCompetitorId(Long id) {
        log.debug("Request to get all techTools by competitor id {}", id);
        return techToolsRepository.findAllByCompetitorId(id).stream()
            .map(techToolsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
