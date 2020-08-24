package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.Workforce;
import fr.ippon.kompetitors.service.WorkforceService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.Workforce}.
 */
@RestController
@RequestMapping("/api")
public class WorkforceResource {

    private final Logger log = LoggerFactory.getLogger(WorkforceResource.class);

    private static final String ENTITY_NAME = "workforce";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkforceService workforceService;

    public WorkforceResource(WorkforceService workforceService) {
        this.workforceService = workforceService;
    }

    /**
     * {@code POST  /workforces} : Create a new workforce.
     *
     * @param workforce the workforce to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workforce, or with status {@code 400 (Bad Request)} if the workforce has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workforces")
    public ResponseEntity<Workforce> createWorkforce(@Valid @RequestBody Workforce workforce) throws URISyntaxException {
        log.debug("REST request to save Workforce : {}", workforce);
        if (workforce.getId() != null) {
            throw new BadRequestAlertException("A new workforce cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Workforce result = workforceService.save(workforce);
        return ResponseEntity.created(new URI("/api/workforces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workforces} : Updates an existing workforce.
     *
     * @param workforce the workforce to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workforce,
     * or with status {@code 400 (Bad Request)} if the workforce is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workforce couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workforces")
    public ResponseEntity<Workforce> updateWorkforce(@Valid @RequestBody Workforce workforce) throws URISyntaxException {
        log.debug("REST request to update Workforce : {}", workforce);
        if (workforce.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Workforce result = workforceService.save(workforce);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workforce.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /workforces} : get all the workforces.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workforces in body.
     */
    @GetMapping("/workforces")
    public List<Workforce> getAllWorkforces() {
        log.debug("REST request to get all Workforces");
        return workforceService.findAll();
    }

    /**
     * {@code GET  /workforces/competitor/{competitorId}} : get workforces by competitor_id.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workforces in body.
     */
    @GetMapping("/workforces/competitor/{competitorId}")
    public List<Workforce> getWorkorcesByCompetitorId(@PathVariable Long competitorId) {
        log.debug("REST request to get Workforces by competitor_id");
        return workforceService.findByCompetitorId(competitorId);
    }

    /**
     * {@code GET  /workforces/:id} : get the "id" workforce.
     *
     * @param id the id of the workforce to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workforce, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workforces/{id}")
    public ResponseEntity<Workforce> getWorkforce(@PathVariable Long id) {
        log.debug("REST request to get Workforce : {}", id);
        Optional<Workforce> workforce = workforceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workforce);
    }

    /**
     * {@code DELETE  /workforces/:id} : delete the "id" workforce.
     *
     * @param id the id of the workforce to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workforces/{id}")
    public ResponseEntity<Void> deleteWorkforce(@PathVariable Long id) {
        log.debug("REST request to delete Workforce : {}", id);
        workforceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
