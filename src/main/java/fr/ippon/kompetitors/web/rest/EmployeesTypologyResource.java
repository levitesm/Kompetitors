package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.EmployeesTypologyService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.EmployeesTypologyDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.EmployeesTypology}.
 */
@RestController
@RequestMapping("/api")
public class EmployeesTypologyResource {

    private final Logger log = LoggerFactory.getLogger(EmployeesTypologyResource.class);

    private static final String ENTITY_NAME = "employeesTypology";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeesTypologyService employeesTypologyService;

    public EmployeesTypologyResource(EmployeesTypologyService employeesTypologyService) {
        this.employeesTypologyService = employeesTypologyService;
    }

    /**
     * {@code POST  /employees-typologies} : Create a new employeesTypology.
     *
     * @param employeesTypologyDTO the employeesTypologyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeesTypologyDTO, or with status {@code 400 (Bad Request)} if the employeesTypology has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees-typologies")
    public ResponseEntity<EmployeesTypologyDTO> createEmployeesTypology(@RequestBody EmployeesTypologyDTO employeesTypologyDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeesTypology : {}", employeesTypologyDTO);
        if (employeesTypologyDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeesTypology cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeesTypologyDTO result = employeesTypologyService.save(employeesTypologyDTO);
        return ResponseEntity.created(new URI("/api/employees-typologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employees-typologies} : Updates an existing employeesTypology.
     *
     * @param employeesTypologyDTO the employeesTypologyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeesTypologyDTO,
     * or with status {@code 400 (Bad Request)} if the employeesTypologyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeesTypologyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employees-typologies")
    public ResponseEntity<EmployeesTypologyDTO> updateEmployeesTypology(@RequestBody EmployeesTypologyDTO employeesTypologyDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeesTypology : {}", employeesTypologyDTO);
        if (employeesTypologyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeesTypologyDTO result = employeesTypologyService.save(employeesTypologyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeesTypologyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employees-typologies} : get all the employeesTypologies.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeesTypologies in body.
     */
    @GetMapping("/employees-typologies")
    public ResponseEntity<List<EmployeesTypologyDTO>> getAllEmployeesTypologies(Pageable pageable) {
        log.debug("REST request to get a page of EmployeesTypologies");
        Page<EmployeesTypologyDTO> page = employeesTypologyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employees-typologies/:id} : get the "id" employeesTypology.
     *
     * @param id the id of the employeesTypologyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeesTypologyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees-typologies/{id}")
    public ResponseEntity<EmployeesTypologyDTO> getEmployeesTypology(@PathVariable Long id) {
        log.debug("REST request to get EmployeesTypology : {}", id);
        Optional<EmployeesTypologyDTO> employeesTypologyDTO = employeesTypologyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeesTypologyDTO);
    }

    /**
     * {@code DELETE  /employees-typologies/:id} : delete the "id" employeesTypology.
     *
     * @param id the id of the employeesTypologyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employees-typologies/{id}")
    public ResponseEntity<Void> deleteEmployeesTypology(@PathVariable Long id) {
        log.debug("REST request to delete EmployeesTypology : {}", id);
        employeesTypologyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /employees-typologies/competitor/:id} : get employeesTypologies by competitor id for three years.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeesTypologies in body.
     */
    @GetMapping("/employees-typologies/competitor/{id}")
    public ResponseEntity<List<EmployeesTypologyDTO>> getEmployeesTypologiesByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get employeesTypologies by competitor id {} for three years", id);
        return ResponseEntity.ok(employeesTypologyService.findByCompetitorId(id));
    }

    /**
     * {@code POST  /employees-typologies/all} : Create or update list of employeesTypology.
     *
     * @param typologies the list of employeesTypologyDTO to create or update.
     * @return the {@link ResponseEntity} with body the updated employeesTypologyDTO.
     */
    @PostMapping("/employees-typologies/all")
    public ResponseEntity<List<EmployeesTypologyDTO>> updateAll(@RequestBody List<EmployeesTypologyDTO> typologies) {
        log.debug("REST request to Create or update list of employeesTypology {}", typologies);
        List<EmployeesTypologyDTO> result = employeesTypologyService.updateAll(typologies);
        return ResponseEntity.ok(result);
    }
}
