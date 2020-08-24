package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.TechPartners;
import fr.ippon.kompetitors.repository.TechPartnersRepository;
import fr.ippon.kompetitors.service.dto.TechPartnersDTO;
import fr.ippon.kompetitors.service.mapper.TechPartnersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TechPartners}.
 */
@Service
@Transactional
public class TechPartnersService {

    private final Logger log = LoggerFactory.getLogger(TechPartnersService.class);

    private final TechPartnersRepository techPartnersRepository;

    private final TechPartnersMapper techPartnersMapper;

    public TechPartnersService(TechPartnersRepository techPartnersRepository, TechPartnersMapper techPartnersMapper) {
        this.techPartnersRepository = techPartnersRepository;
        this.techPartnersMapper = techPartnersMapper;
    }

    /**
     * Save a techPartners.
     *
     * @param techPartnersDTO the entity to save.
     * @return the persisted entity.
     */
    public TechPartnersDTO save(TechPartnersDTO techPartnersDTO) {
        log.debug("Request to save TechPartners : {}", techPartnersDTO);
        TechPartners techPartners = techPartnersMapper.toEntity(techPartnersDTO);
        techPartners = techPartnersRepository.save(techPartners);
        return techPartnersMapper.toDto(techPartners);
    }

    /**
     * Get all the techPartners.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechPartnersDTO> findAll() {
        log.debug("Request to get all TechPartners");
        return techPartnersRepository.findAll().stream()
            .map(techPartnersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one techPartners by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechPartnersDTO> findOne(Long id) {
        log.debug("Request to get TechPartners : {}", id);
        return techPartnersRepository.findById(id)
            .map(techPartnersMapper::toDto);
    }

    /**
     * Delete the techPartners by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechPartners : {}", id);
        techPartnersRepository.deleteById(id);
    }

    /**
     * Get all the techPartners by competitor id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechPartnersDTO> findAllByCompetitorId(Long id) {
        log.debug("Request to get all TechPartners by competitor id {}", id);
        return techPartnersRepository.findAllByCompetitorId(id).stream()
            .map(techPartnersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
