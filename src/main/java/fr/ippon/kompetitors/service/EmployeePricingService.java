package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.EmployeePricing;
import fr.ippon.kompetitors.repository.EmployeePricingRepository;
import fr.ippon.kompetitors.service.dto.EmployeePricingDTO;
import fr.ippon.kompetitors.service.mapper.EmployeePricingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EmployeePricing}.
 */
@Service
@Transactional
public class EmployeePricingService {

    private final Logger log = LoggerFactory.getLogger(EmployeePricingService.class);

    private final EmployeePricingRepository employeePricingRepository;

    private final EmployeePricingMapper employeePricingMapper;

    public EmployeePricingService(EmployeePricingRepository employeePricingRepository, EmployeePricingMapper employeePricingMapper) {
        this.employeePricingRepository = employeePricingRepository;
        this.employeePricingMapper = employeePricingMapper;
    }

    /**
     * Save a employeePricing.
     *
     * @param employeePricingDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeePricingDTO save(EmployeePricingDTO employeePricingDTO) {
        log.debug("Request to save EmployeePricing : {}", employeePricingDTO);
        EmployeePricing employeePricing = employeePricingMapper.toEntity(employeePricingDTO);
        employeePricing = employeePricingRepository.save(employeePricing);
        return employeePricingMapper.toDto(employeePricing);
    }

    /**
     * Get all the employeePricings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeePricingDTO> findAll() {
        log.debug("Request to get all EmployeePricings");
        return employeePricingRepository.findAll().stream()
            .map(employeePricingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one employeePricing by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeePricingDTO> findOne(Long id) {
        log.debug("Request to get EmployeePricing : {}", id);
        return employeePricingRepository.findById(id)
            .map(employeePricingMapper::toDto);
    }

    /**
     * Delete the employeePricing by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeePricing : {}", id);
        employeePricingRepository.deleteById(id);
    }

    /**
     * Get all the employeePricings by competitor id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeePricingDTO> findAllByCompetitorId(Long competitorId) {
        log.debug("Request to get all EmployeePricings by competitor id");
        return employeePricingRepository.findAllByCompetitorId(competitorId).stream()
            .map(employeePricingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
