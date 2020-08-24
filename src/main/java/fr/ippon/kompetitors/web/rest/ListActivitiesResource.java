package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ListActivities;
import fr.ippon.kompetitors.repository.ListActivitiesRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListActivities}.
 */
@RestController
@RequestMapping("/api")
public class ListActivitiesResource {

    private final Logger log = LoggerFactory.getLogger(ListActivitiesResource.class);

    private static final String ENTITY_NAME = "listActivities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListActivitiesRepository listActivitiesRepository;

    public ListActivitiesResource(ListActivitiesRepository listActivitiesRepository) {
        this.listActivitiesRepository = listActivitiesRepository;
    }

    /**
     * {@code POST  /list-activities} : Create a new listActivities.
     *
     * @param listActivities the listActivities to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listActivities, or with status {@code 400 (Bad Request)} if the listActivities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-activities")
    public ResponseEntity<ListActivities> createListActivities(@Valid @RequestBody ListActivities listActivities) throws URISyntaxException {
        log.debug("REST request to save ListActivities : {}", listActivities);
        if (listActivities.getId() != null) {
            throw new BadRequestAlertException("A new listActivities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListActivities result = listActivitiesRepository.save(listActivities);
        return ResponseEntity.created(new URI("/api/list-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-activities} : Updates an existing listActivities.
     *
     * @param listActivities the listActivities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listActivities,
     * or with status {@code 400 (Bad Request)} if the listActivities is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listActivities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-activities")
    public ResponseEntity<ListActivities> updateListActivities(@Valid @RequestBody ListActivities listActivities) throws URISyntaxException {
        log.debug("REST request to update ListActivities : {}", listActivities);
        if (listActivities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListActivities result = listActivitiesRepository.save(listActivities);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listActivities.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-activities} : get all the listActivities.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listActivities in body.
     */
    @GetMapping("/list-activities")
    public List<ListActivities> getAllListActivities() {
        log.debug("REST request to get all ListActivities");
        return listActivitiesRepository.findAll();
    }

    /**
     * {@code GET  /list-activities/:id} : get the "id" listActivities.
     *
     * @param id the id of the listActivities to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listActivities, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-activities/{id}")
    public ResponseEntity<ListActivities> getListActivities(@PathVariable Long id) {
        log.debug("REST request to get ListActivities : {}", id);
        Optional<ListActivities> listActivities = listActivitiesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listActivities);
    }

    /**
     * {@code DELETE  /list-activities/:id} : delete the "id" listActivities.
     *
     * @param id the id of the listActivities to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-activities/{id}")
    public ResponseEntity<Void> deleteListActivities(@PathVariable Long id) {
        log.debug("REST request to delete ListActivities : {}", id);
        listActivitiesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
