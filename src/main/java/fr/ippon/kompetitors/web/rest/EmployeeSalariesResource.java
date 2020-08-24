package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.EmployeeSalaries;
import fr.ippon.kompetitors.repository.EmployeeSalariesRepository;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.EmployeeSalaries}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeSalariesResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeSalariesResource.class);

    private static final String ENTITY_NAME = "employeeSalaries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeSalariesRepository employeeSalariesRepository;

    public EmployeeSalariesResource(EmployeeSalariesRepository employeeSalariesRepository) {
        this.employeeSalariesRepository = employeeSalariesRepository;
    }

    /**
     * {@code POST  /employee-salaries} : Create a new employeeSalaries.
     *
     * @param employeeSalaries the employeeSalaries to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeSalaries, or with status {@code 400 (Bad Request)} if the employeeSalaries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-salaries")
    public ResponseEntity<EmployeeSalaries> createEmployeeSalaries(@RequestBody EmployeeSalaries employeeSalaries) throws URISyntaxException {
        log.debug("REST request to save EmployeeSalaries : {}", employeeSalaries);
        if (employeeSalaries.getId() != null) {
            throw new BadRequestAlertException("A new employeeSalaries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeSalaries result = employeeSalariesRepository.save(employeeSalaries);
        return ResponseEntity.created(new URI("/api/employee-salaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-salaries} : Updates an existing employeeSalaries.
     *
     * @param employeeSalaries the employeeSalaries to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeSalaries,
     * or with status {@code 400 (Bad Request)} if the employeeSalaries is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeSalaries couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-salaries")
    public ResponseEntity<EmployeeSalaries> updateEmployeeSalaries(@RequestBody EmployeeSalaries employeeSalaries) throws URISyntaxException {
        log.debug("REST request to update EmployeeSalaries : {}", employeeSalaries);
        if (employeeSalaries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeSalaries result = employeeSalariesRepository.save(employeeSalaries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeSalaries.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-salaries} : get all the employeeSalaries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeSalaries in body.
     */
    @GetMapping("/employee-salaries")
    public List<EmployeeSalaries> getAllEmployeeSalaries() {
        log.debug("REST request to get all EmployeeSalaries");
        return employeeSalariesRepository.findAll();
    }

    /**
     * {@code GET  /employee-salaries/:id} : get the "id" employeeSalaries.
     *
     * @param id the id of the employeeSalaries to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeSalaries, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-salaries/{id}")
    public ResponseEntity<EmployeeSalaries> getEmployeeSalaries(@PathVariable Long id) {
        log.debug("REST request to get EmployeeSalaries : {}", id);
        Optional<EmployeeSalaries> employeeSalaries = employeeSalariesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employeeSalaries);
    }

    /**
     * {@code DELETE  /employee-salaries/:id} : delete the "id" employeeSalaries.
     *
     * @param id the id of the employeeSalaries to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-salaries/{id}")
    public ResponseEntity<Void> deleteEmployeeSalaries(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeSalaries : {}", id);
        employeeSalariesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /employee-salaries/competitor/:id} : get the "competitorId" employeeSalaries.
     *
     * @param id the id of the competitor.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeSalaries, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-salaries/competitor/{id}")
    public List<EmployeeSalaries> getEmployeeSalariesByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get EmployeeSalaries for competitorId : {}", id);
        return employeeSalariesRepository.getEmployeeSalariesByCompetitorId(id);
    }
}
