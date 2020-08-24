package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.EmployeePricingService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.EmployeePricingDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.EmployeePricing}.
 */
@RestController
@RequestMapping("/api")
public class EmployeePricingResource {

    private final Logger log = LoggerFactory.getLogger(EmployeePricingResource.class);

    private static final String ENTITY_NAME = "employeePricing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeePricingService employeePricingService;

    public EmployeePricingResource(EmployeePricingService employeePricingService) {
        this.employeePricingService = employeePricingService;
    }

    /**
     * {@code POST  /employee-pricings} : Create a new employeePricing.
     *
     * @param employeePricingDTO the employeePricingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeePricingDTO, or with status {@code 400 (Bad Request)} if the employeePricing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-pricings")
    public ResponseEntity<EmployeePricingDTO> createEmployeePricing(@RequestBody EmployeePricingDTO employeePricingDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeePricing : {}", employeePricingDTO);
        if (employeePricingDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeePricing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeePricingDTO result = employeePricingService.save(employeePricingDTO);
        return ResponseEntity.created(new URI("/api/employee-pricings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-pricings} : Updates an existing employeePricing.
     *
     * @param employeePricingDTO the employeePricingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeePricingDTO,
     * or with status {@code 400 (Bad Request)} if the employeePricingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeePricingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-pricings")
    public ResponseEntity<EmployeePricingDTO> updateEmployeePricing(@RequestBody EmployeePricingDTO employeePricingDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeePricing : {}", employeePricingDTO);
        if (employeePricingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeePricingDTO result = employeePricingService.save(employeePricingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeePricingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-pricings} : get all the employeePricings.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeePricings in body.
     */
    @GetMapping("/employee-pricings")
    public List<EmployeePricingDTO> getAllEmployeePricings() {
        log.debug("REST request to get all EmployeePricings");
        return employeePricingService.findAll();
    }

    /**
     * {@code GET  /employee-pricings/:id} : get the "id" employeePricing.
     *
     * @param id the id of the employeePricingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeePricingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-pricings/{id}")
    public ResponseEntity<EmployeePricingDTO> getEmployeePricing(@PathVariable Long id) {
        log.debug("REST request to get EmployeePricing : {}", id);
        Optional<EmployeePricingDTO> employeePricingDTO = employeePricingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeePricingDTO);
    }

    /**
     * {@code DELETE  /employee-pricings/:id} : delete the "id" employeePricing.
     *
     * @param id the id of the employeePricingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-pricings/{id}")
    public ResponseEntity<Void> deleteEmployeePricing(@PathVariable Long id) {
        log.debug("REST request to delete EmployeePricing : {}", id);
        employeePricingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /employee-pricings/competitor/:id} : get the "id" employeePricing.
     *
     * @param id the id of the employeePricingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeePricingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-pricings/competitor/{id}")
    public ResponseEntity<List<EmployeePricingDTO>> getEmployeePricingByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get EmployeePricings by competitor id : {}", id);
        List<EmployeePricingDTO> result = employeePricingService.findAllByCompetitorId(id);
        return ResponseEntity.ok(result);
    }
}
