package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.TechServices;
import fr.ippon.kompetitors.repository.TechServicesRepository;
import fr.ippon.kompetitors.service.dto.TechServicesDTO;
import fr.ippon.kompetitors.service.mapper.TechServicesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TechServices}.
 */
@Service
@Transactional
public class TechServicesService {

    private final Logger log = LoggerFactory.getLogger(TechServicesService.class);

    private final TechServicesRepository techServicesRepository;

    private final TechServicesMapper techServicesMapper;

    public TechServicesService(TechServicesRepository techServicesRepository, TechServicesMapper techServicesMapper) {
        this.techServicesRepository = techServicesRepository;
        this.techServicesMapper = techServicesMapper;
    }

    /**
     * Save a techServices.
     *
     * @param techServicesDTO the entity to save.
     * @return the persisted entity.
     */
    public TechServicesDTO save(TechServicesDTO techServicesDTO) {
        log.debug("Request to save TechServices : {}", techServicesDTO);
        TechServices techServices = techServicesMapper.toEntity(techServicesDTO);
        techServices = techServicesRepository.save(techServices);
        return techServicesMapper.toDto(techServices);
    }

    /**
     * Get all the techServices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechServicesDTO> findAll() {
        log.debug("Request to get all TechServices");
        return techServicesRepository.findAll().stream()
            .map(techServicesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one techServices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechServicesDTO> findOne(Long id) {
        log.debug("Request to get TechServices : {}", id);
        return techServicesRepository.findById(id)
            .map(techServicesMapper::toDto);
    }

    /**
     * Delete the techServices by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechServices : {}", id);
        techServicesRepository.deleteById(id);
    }

    /**
     * Get all the techServices by competitor id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechServicesDTO> findAllByCompetitorId(Long id) {
        log.debug("Request to get all techServices by competitor id {}", id);
        return techServicesRepository.findAllByCompetitorId(id).stream()
            .map(techServicesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
