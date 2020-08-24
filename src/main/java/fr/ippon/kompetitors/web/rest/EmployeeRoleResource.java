package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.EmployeeRoleService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.EmployeeRoleDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.EmployeeRole}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeRoleResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeRoleResource.class);

    private static final String ENTITY_NAME = "employeeRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeRoleService employeeRoleService;

    public EmployeeRoleResource(EmployeeRoleService employeeRoleService) {
        this.employeeRoleService = employeeRoleService;
    }

    /**
     * {@code POST  /employee-roles} : Create a new employeeRole.
     *
     * @param employeeRoleDTO the employeeRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeRoleDTO, or with status {@code 400 (Bad Request)} if the employeeRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-roles")
    public ResponseEntity<EmployeeRoleDTO> createEmployeeRole(@Valid @RequestBody EmployeeRoleDTO employeeRoleDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeRole : {}", employeeRoleDTO);
        if (employeeRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeRoleDTO result = employeeRoleService.save(employeeRoleDTO);
        return ResponseEntity.created(new URI("/api/employee-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-roles} : Updates an existing employeeRole.
     *
     * @param employeeRoleDTO the employeeRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeRoleDTO,
     * or with status {@code 400 (Bad Request)} if the employeeRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-roles")
    public ResponseEntity<EmployeeRoleDTO> updateEmployeeRole(@Valid @RequestBody EmployeeRoleDTO employeeRoleDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeRole : {}", employeeRoleDTO);
        if (employeeRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeRoleDTO result = employeeRoleService.save(employeeRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-roles} : get all the employeeRoles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeRoles in body.
     */
    @GetMapping("/employee-roles")
    public List<EmployeeRoleDTO> getAllEmployeeRoles() {
        log.debug("REST request to get all EmployeeRoles");
        return employeeRoleService.findAll();
    }

    /**
     * {@code GET  /employee-roles/:id} : get the "id" employeeRole.
     *
     * @param id the id of the employeeRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-roles/{id}")
    public ResponseEntity<EmployeeRoleDTO> getEmployeeRole(@PathVariable Long id) {
        log.debug("REST request to get EmployeeRole : {}", id);
        Optional<EmployeeRoleDTO> employeeRoleDTO = employeeRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeRoleDTO);
    }

    /**
     * {@code DELETE  /employee-roles/:id} : delete the "id" employeeRole.
     *
     * @param id the id of the employeeRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-roles/{id}")
    public ResponseEntity<Void> deleteEmployeeRole(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeRole : {}", id);
        employeeRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
