package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.Legal;
import fr.ippon.kompetitors.repository.LegalRepository;
import fr.ippon.kompetitors.service.SocieteService;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.Legal}.
 */
@RestController
@RequestMapping("/api")
public class LegalResource {

    private final Logger log = LoggerFactory.getLogger(LegalResource.class);

    private static final String ENTITY_NAME = "legal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LegalRepository legalRepository;

    private final SocieteService societeService;

    public LegalResource(LegalRepository legalRepository, SocieteService societeService) {
        this.legalRepository = legalRepository;
        this.societeService = societeService;
    }

    /**
     * {@code POST  /legals} : Create a new legal.
     *
     * @param legal the legal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new legal, or with status {@code 400 (Bad Request)} if the legal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/legals")
    public ResponseEntity<Legal> createLegal(@RequestBody Legal legal) throws URISyntaxException {
        log.debug("REST request to save Legal : {}", legal);
        if (legal.getId() != null) {
            throw new BadRequestAlertException("A new legal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Legal result = legalRepository.save(legal);
        return ResponseEntity.created(new URI("/api/legals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /legals} : Updates an existing legal.
     *
     * @param legal the legal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legal,
     * or with status {@code 400 (Bad Request)} if the legal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the legal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/legals")
    public ResponseEntity<Legal> updateLegal(@RequestBody Legal legal) throws URISyntaxException {
        log.debug("REST request to update Legal : {}", legal);
        if (legal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Legal result = legalRepository.save(legal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, legal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /legals} : get all the legals.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of legals in body.
     */
    @GetMapping("/legals")
    public List<Legal> getAllLegals() {
        log.debug("REST request to get all Legals");
        return legalRepository.findAll();
    }

    /**
     * {@code GET  /legals/:id} : get the "id" legal.
     *
     * @param id the id of the legal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the legal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/legals/{id}")
    public ResponseEntity<Legal> getLegal(@PathVariable Long id) {
        log.debug("REST request to get Legal : {}", id);
        Optional<Legal> legal = legalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(legal);
    }

    /**
     * {@code DELETE  /legals/:id} : delete the "id" legal.
     *
     * @param id the id of the legal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/legals/{id}")
    public ResponseEntity<Void> deleteLegal(@PathVariable Long id) {
        log.debug("REST request to delete Legal : {}", id);
        legalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
