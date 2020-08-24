package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.EmployeeType;
import fr.ippon.kompetitors.repository.EmployeeTypeRepository;
import fr.ippon.kompetitors.service.dto.EmployeeTypeDTO;
import fr.ippon.kompetitors.service.mapper.EmployeeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EmployeeType}.
 */
@Service
@Transactional
public class EmployeeTypeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeTypeService.class);

    private final EmployeeTypeRepository employeeTypeRepository;

    private final EmployeeTypeMapper employeeTypeMapper;

    public EmployeeTypeService(EmployeeTypeRepository employeeTypeRepository, EmployeeTypeMapper employeeTypeMapper) {
        this.employeeTypeRepository = employeeTypeRepository;
        this.employeeTypeMapper = employeeTypeMapper;
    }

    /**
     * Save a employeeType.
     *
     * @param employeeTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeTypeDTO save(EmployeeTypeDTO employeeTypeDTO) {
        log.debug("Request to save EmployeeType : {}", employeeTypeDTO);
        EmployeeType employeeType = employeeTypeMapper.toEntity(employeeTypeDTO);
        employeeType = employeeTypeRepository.save(employeeType);
        return employeeTypeMapper.toDto(employeeType);
    }

    /**
     * Get all the employeeTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeTypeDTO> findAll() {
        log.debug("Request to get all EmployeeTypes");
        return employeeTypeRepository.findAll().stream()
            .map(employeeTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one employeeType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeTypeDTO> findOne(Long id) {
        log.debug("Request to get EmployeeType : {}", id);
        return employeeTypeRepository.findById(id)
            .map(employeeTypeMapper::toDto);
    }

    /**
     * Delete the employeeType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeType : {}", id);
        employeeTypeRepository.deleteById(id);
    }
}
