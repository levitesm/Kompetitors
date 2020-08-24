package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.TechPractices;
import fr.ippon.kompetitors.repository.TechPracticesRepository;
import fr.ippon.kompetitors.service.dto.TechPracticesDTO;
import fr.ippon.kompetitors.service.mapper.TechPracticesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TechPractices}.
 */
@Service
@Transactional
public class TechPracticesService {

    private final Logger log = LoggerFactory.getLogger(TechPracticesService.class);

    private final TechPracticesRepository techPracticesRepository;

    private final TechPracticesMapper techPracticesMapper;

    public TechPracticesService(TechPracticesRepository techPracticesRepository, TechPracticesMapper techPracticesMapper) {
        this.techPracticesRepository = techPracticesRepository;
        this.techPracticesMapper = techPracticesMapper;
    }

    /**
     * Save a techPractices.
     *
     * @param techPracticesDTO the entity to save.
     * @return the persisted entity.
     */
    public TechPracticesDTO save(TechPracticesDTO techPracticesDTO) {
        log.debug("Request to save TechPractices : {}", techPracticesDTO);
        TechPractices techPractices = techPracticesMapper.toEntity(techPracticesDTO);
        techPractices = techPracticesRepository.save(techPractices);
        return techPracticesMapper.toDto(techPractices);
    }

    /**
     * Get all the techPractices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechPracticesDTO> findAll() {
        log.debug("Request to get all TechPractices");
        return techPracticesRepository.findAll().stream()
            .map(techPracticesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one techPractices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechPracticesDTO> findOne(Long id) {
        log.debug("Request to get TechPractices : {}", id);
        return techPracticesRepository.findById(id)
            .map(techPracticesMapper::toDto);
    }

    /**
     * Delete the techPractices by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechPractices : {}", id);
        techPracticesRepository.deleteById(id);
    }

    /**
     * Get all the techPractices by competitor id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechPracticesDTO> findAllByCompetitorId(Long id) {
        log.debug("Request to get all TechPractices by competitor id {}", id);
        return techPracticesRepository.findAllByCompetitorId(id).stream()
            .map(techPracticesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
