package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.EmployeeRole;
import fr.ippon.kompetitors.repository.EmployeeRoleRepository;
import fr.ippon.kompetitors.service.dto.EmployeeRoleDTO;
import fr.ippon.kompetitors.service.mapper.EmployeeRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EmployeeRole}.
 */
@Service
@Transactional
public class EmployeeRoleService {

    private final Logger log = LoggerFactory.getLogger(EmployeeRoleService.class);

    private final EmployeeRoleRepository employeeRoleRepository;

    private final EmployeeRoleMapper employeeRoleMapper;

    public EmployeeRoleService(EmployeeRoleRepository employeeRoleRepository, EmployeeRoleMapper employeeRoleMapper) {
        this.employeeRoleRepository = employeeRoleRepository;
        this.employeeRoleMapper = employeeRoleMapper;
    }

    /**
     * Save a employeeRole.
     *
     * @param employeeRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeRoleDTO save(EmployeeRoleDTO employeeRoleDTO) {
        log.debug("Request to save EmployeeRole : {}", employeeRoleDTO);
        EmployeeRole employeeRole = employeeRoleMapper.toEntity(employeeRoleDTO);
        employeeRole = employeeRoleRepository.save(employeeRole);
        return employeeRoleMapper.toDto(employeeRole);
    }

    /**
     * Get all the employeeRoles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeRoleDTO> findAll() {
        log.debug("Request to get all EmployeeRoles");
        return employeeRoleRepository.findAll().stream()
            .map(employeeRoleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one employeeRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeRoleDTO> findOne(Long id) {
        log.debug("Request to get EmployeeRole : {}", id);
        return employeeRoleRepository.findById(id)
            .map(employeeRoleMapper::toDto);
    }

    /**
     * Delete the employeeRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeRole : {}", id);
        employeeRoleRepository.deleteById(id);
    }
}
