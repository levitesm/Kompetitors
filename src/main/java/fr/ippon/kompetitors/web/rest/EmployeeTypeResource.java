package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.EmployeeTypeService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.EmployeeTypeDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.EmployeeType}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeTypeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeTypeResource.class);

    private static final String ENTITY_NAME = "employeeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeTypeService employeeTypeService;

    public EmployeeTypeResource(EmployeeTypeService employeeTypeService) {
        this.employeeTypeService = employeeTypeService;
    }

    /**
     * {@code POST  /employee-types} : Create a new employeeType.
     *
     * @param employeeTypeDTO the employeeTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeTypeDTO, or with status {@code 400 (Bad Request)} if the employeeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-types")
    public ResponseEntity<EmployeeTypeDTO> createEmployeeType(@Valid @RequestBody EmployeeTypeDTO employeeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeType : {}", employeeTypeDTO);
        if (employeeTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeTypeDTO result = employeeTypeService.save(employeeTypeDTO);
        return ResponseEntity.created(new URI("/api/employee-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-types} : Updates an existing employeeType.
     *
     * @param employeeTypeDTO the employeeTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeTypeDTO,
     * or with status {@code 400 (Bad Request)} if the employeeTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-types")
    public ResponseEntity<EmployeeTypeDTO> updateEmployeeType(@Valid @RequestBody EmployeeTypeDTO employeeTypeDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeType : {}", employeeTypeDTO);
        if (employeeTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeTypeDTO result = employeeTypeService.save(employeeTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-types} : get all the employeeTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeTypes in body.
     */
    @GetMapping("/employee-types")
    public List<EmployeeTypeDTO> getAllEmployeeTypes() {
        log.debug("REST request to get all EmployeeTypes");
        return employeeTypeService.findAll();
    }

    /**
     * {@code GET  /employee-types/:id} : get the "id" employeeType.
     *
     * @param id the id of the employeeTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-types/{id}")
    public ResponseEntity<EmployeeTypeDTO> getEmployeeType(@PathVariable Long id) {
        log.debug("REST request to get EmployeeType : {}", id);
        Optional<EmployeeTypeDTO> employeeTypeDTO = employeeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeTypeDTO);
    }

    /**
     * {@code DELETE  /employee-types/:id} : delete the "id" employeeType.
     *
     * @param id the id of the employeeTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-types/{id}")
    public ResponseEntity<Void> deleteEmployeeType(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeType : {}", id);
        employeeTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
