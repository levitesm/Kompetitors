package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.Updatehistory;
import fr.ippon.kompetitors.repository.UpdatehistoryRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.Updatehistory}.
 */
@RestController
@RequestMapping("/api")
public class UpdatehistoryResource {

    private final Logger log = LoggerFactory.getLogger(UpdatehistoryResource.class);

    private static final String ENTITY_NAME = "updatehistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UpdatehistoryRepository updatehistoryRepository;

    public UpdatehistoryResource(UpdatehistoryRepository updatehistoryRepository) {
        this.updatehistoryRepository = updatehistoryRepository;
    }

    /**
     * {@code POST  /updatehistories} : Create a new updatehistory.
     *
     * @param updatehistory the updatehistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new updatehistory, or with status {@code 400 (Bad Request)} if the updatehistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/updatehistories")
    public ResponseEntity<Updatehistory> createUpdatehistory(@RequestBody Updatehistory updatehistory) throws URISyntaxException {
        log.debug("REST request to save Updatehistory : {}", updatehistory);
        if (updatehistory.getId() != null) {
            throw new BadRequestAlertException("A new updatehistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Updatehistory result = updatehistoryRepository.save(updatehistory);
        return ResponseEntity.created(new URI("/api/updatehistories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /updatehistories} : Updates an existing updatehistory.
     *
     * @param updatehistory the updatehistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated updatehistory,
     * or with status {@code 400 (Bad Request)} if the updatehistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the updatehistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/updatehistories")
    public ResponseEntity<Updatehistory> updateUpdatehistory(@RequestBody Updatehistory updatehistory) throws URISyntaxException {
        log.debug("REST request to update Updatehistory : {}", updatehistory);
        if (updatehistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Updatehistory result = updatehistoryRepository.save(updatehistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, updatehistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /updatehistories} : get all the updatehistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of updatehistories in body.
     */
    @GetMapping("/updatehistories")
    public List<Updatehistory> getAllUpdatehistories() {
        log.debug("REST request to get all Updatehistories");
        return updatehistoryRepository.findAll();
    }

    /**
     * {@code GET  /updatehistories/:id} : get the "id" updatehistory.
     *
     * @param id the id of the updatehistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updatehistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/updatehistories/{id}")
    public ResponseEntity<Updatehistory> getUpdatehistory(@PathVariable Long id) {
        log.debug("REST request to get Updatehistory : {}", id);
        Optional<Updatehistory> updatehistory = updatehistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(updatehistory);
    }

    /**
     * {@code DELETE  /updatehistories/:id} : delete the "id" updatehistory.
     *
     * @param id the id of the updatehistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/updatehistories/{id}")
    public ResponseEntity<Void> deleteUpdatehistory(@PathVariable Long id) {
        log.debug("REST request to delete Updatehistory : {}", id);
        updatehistoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
