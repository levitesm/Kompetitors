package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.SocieteMain;
import fr.ippon.kompetitors.repository.SocieteMainRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.SocieteMain}.
 */
@RestController
@RequestMapping("/api")
public class SocieteMainResource {

    private final Logger log = LoggerFactory.getLogger(SocieteMainResource.class);

    private static final String ENTITY_NAME = "societeMain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocieteMainRepository societeMainRepository;

    public SocieteMainResource(SocieteMainRepository societeMainRepository) {
        this.societeMainRepository = societeMainRepository;
    }

    /**
     * {@code POST  /societe-mains} : Create a new societeMain.
     *
     * @param societeMain the societeMain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societeMain, or with status {@code 400 (Bad Request)} if the societeMain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/societe-mains")
    public ResponseEntity<SocieteMain> createSocieteMain(@RequestBody SocieteMain societeMain) throws URISyntaxException {
        log.debug("REST request to save SocieteMain : {}", societeMain);
        if (societeMain.getId() != null) {
            throw new BadRequestAlertException("A new societeMain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocieteMain result = societeMainRepository.save(societeMain);
        return ResponseEntity.created(new URI("/api/societe-mains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /societe-mains} : Updates an existing societeMain.
     *
     * @param societeMain the societeMain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societeMain,
     * or with status {@code 400 (Bad Request)} if the societeMain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societeMain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/societe-mains")
    public ResponseEntity<SocieteMain> updateSocieteMain(@RequestBody SocieteMain societeMain) throws URISyntaxException {
        log.debug("REST request to update SocieteMain : {}", societeMain);
        if (societeMain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SocieteMain result = societeMainRepository.save(societeMain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societeMain.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /societe-mains} : get all the societeMains.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societeMains in body.
     */
    @GetMapping("/societe-mains")
    public List<SocieteMain> getAllSocieteMains() {
        log.debug("REST request to get all SocieteMains");
        return societeMainRepository.findAll();
    }

    /**
     * {@code GET  /societe-mains/:id} : get the "id" societeMain.
     *
     * @param id the id of the societeMain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societeMain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/societe-mains/{id}")
    public ResponseEntity<SocieteMain> getSocieteMain(@PathVariable Long id) {
        log.debug("REST request to get SocieteMain : {}", id);
        Optional<SocieteMain> societeMain = societeMainRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(societeMain);
    }

    /**
     * {@code DELETE  /societe-mains/:id} : delete the "id" societeMain.
     *
     * @param id the id of the societeMain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/societe-mains/{id}")
    public ResponseEntity<Void> deleteSocieteMain(@PathVariable Long id) {
        log.debug("REST request to delete SocieteMain : {}", id);
        societeMainRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
