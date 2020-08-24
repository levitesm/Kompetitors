package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.EmployeesTypology;
import fr.ippon.kompetitors.repository.EmployeesTypologyRepository;
import fr.ippon.kompetitors.service.dto.EmployeesTypologyDTO;
import fr.ippon.kompetitors.service.mapper.EmployeesTypologyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EmployeesTypology}.
 */
@Service
@Transactional
public class EmployeesTypologyService {

    private final Logger log = LoggerFactory.getLogger(EmployeesTypologyService.class);

    private final EmployeesTypologyRepository employeesTypologyRepository;

    private final EmployeesTypologyMapper employeesTypologyMapper;

    public EmployeesTypologyService(EmployeesTypologyRepository employeesTypologyRepository, EmployeesTypologyMapper employeesTypologyMapper) {
        this.employeesTypologyRepository = employeesTypologyRepository;
        this.employeesTypologyMapper = employeesTypologyMapper;
    }

    /**
     * Save a employeesTypology.
     *
     * @param employeesTypologyDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeesTypologyDTO save(EmployeesTypologyDTO employeesTypologyDTO) {
        log.debug("Request to save EmployeesTypology : {}", employeesTypologyDTO);
        EmployeesTypology employeesTypology = employeesTypologyMapper.toEntity(employeesTypologyDTO);
        employeesTypology = employeesTypologyRepository.save(employeesTypology);
        return employeesTypologyMapper.toDto(employeesTypology);
    }

    /**
     * Get all the employeesTypologies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeesTypologyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeesTypologies");
        return employeesTypologyRepository.findAll(pageable)
            .map(employeesTypologyMapper::toDto);
    }


    /**
     * Get one employeesTypology by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesTypologyDTO> findOne(Long id) {
        log.debug("Request to get EmployeesTypology : {}", id);
        return employeesTypologyRepository.findById(id)
            .map(employeesTypologyMapper::toDto);
    }

    /**
     * Delete the employeesTypology by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeesTypology : {}", id);
        employeesTypologyRepository.deleteById(id);
    }


    /**
     * Get employeesTypologies by competitor id and year.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeesTypologyDTO> findByCompetitorIdAndYear(Long competitorId, Integer year) {
        log.debug("Request to get EmployeesTypologies by competitor id {} and year {}", competitorId, year);
        return employeesTypologyRepository.findAllByCompetitorIdAndYear(competitorId, year).stream()
            .map(employeesTypologyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get employeesTypologies by competitor id for three years.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeesTypologyDTO> findByCompetitorId(Long competitorId) {
        log.debug("Request to get EmployeesTypologies by competitor id {} for three years", competitorId);
        List<EmployeesTypologyDTO> result = new ArrayList<>();
        getDisplayedYears().forEach(year -> {
            result.addAll(findByCompetitorIdAndYear(competitorId, year));
        });
        return result;
    }

    /**
     * Get list of statistics display year.
     * Previous, current and next year.
     * @return list of statistic years.
     */
    private List<Integer> getDisplayedYears() {
        return new ArrayList<>(Arrays.asList(
            LocalDateTime.now().getYear(),
            LocalDateTime.now().getYear() - 1,
            LocalDateTime.now().getYear() + 1
        ));
    }

    /**
     * Create or update list of employeesTypology.
     *
     * @return the list of entities.
     */
    public List<EmployeesTypologyDTO> updateAll(List<EmployeesTypologyDTO> typologies) {
        log.debug("Request to Create or update list of employeesTypology: {}", typologies);
        return employeesTypologyRepository.saveAll(typologies.stream()
            .map(employeesTypologyMapper::toEntity).collect(Collectors.toList())).stream()
            .map(employeesTypologyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
}
