package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.Infogreffe;
import fr.ippon.kompetitors.repository.InfogreffeRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.Infogreffe}.
 */
@RestController
@RequestMapping("/api")
public class InfogreffeResource {

    private final Logger log = LoggerFactory.getLogger(InfogreffeResource.class);

    private static final String ENTITY_NAME = "infogreffe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfogreffeRepository infogreffeRepository;

    public InfogreffeResource(InfogreffeRepository infogreffeRepository) {
        this.infogreffeRepository = infogreffeRepository;
    }

    /**
     * {@code POST  /infogreffes} : Create a new infogreffe.
     *
     * @param infogreffe the infogreffe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infogreffe, or with status {@code 400 (Bad Request)} if the infogreffe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/infogreffes")
    public ResponseEntity<Infogreffe> createInfogreffe(@RequestBody Infogreffe infogreffe) throws URISyntaxException {
        log.debug("REST request to save Infogreffe : {}", infogreffe);
        if (infogreffe.getId() != null) {
            throw new BadRequestAlertException("A new infogreffe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Infogreffe result = infogreffeRepository.save(infogreffe);
        return ResponseEntity.created(new URI("/api/infogreffes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /infogreffes} : Updates an existing infogreffe.
     *
     * @param infogreffe the infogreffe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infogreffe,
     * or with status {@code 400 (Bad Request)} if the infogreffe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infogreffe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/infogreffes")
    public ResponseEntity<Infogreffe> updateInfogreffe(@RequestBody Infogreffe infogreffe) throws URISyntaxException {
        log.debug("REST request to update Infogreffe : {}", infogreffe);
        if (infogreffe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Infogreffe result = infogreffeRepository.save(infogreffe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infogreffe.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /infogreffes} : get all the infogreffes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infogreffes in body.
     */
    @GetMapping("/infogreffes")
    public List<Infogreffe> getAllInfogreffes() {
        log.debug("REST request to get all Infogreffes");
        return infogreffeRepository.findAll();
    }

    /**
     * {@code GET  /infogreffes/:id} : get the "id" infogreffe.
     *
     * @param id the id of the infogreffe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infogreffe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/infogreffes/{id}")
    public ResponseEntity<Infogreffe> getInfogreffe(@PathVariable Long id) {
        log.debug("REST request to get Infogreffe : {}", id);
        Optional<Infogreffe> infogreffe = infogreffeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(infogreffe);
    }

    /**
     * {@code DELETE  /infogreffes/:id} : delete the "id" infogreffe.
     *
     * @param id the id of the infogreffe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/infogreffes/{id}")
    public ResponseEntity<Void> deleteInfogreffe(@PathVariable Long id) {
        log.debug("REST request to delete Infogreffe : {}", id);
        infogreffeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
