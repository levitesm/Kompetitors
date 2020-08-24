package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ListOwnerships;
import fr.ippon.kompetitors.repository.ListOwnershipsRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListOwnerships}.
 */
@RestController
@RequestMapping("/api")
public class ListOwnershipsResource {

    private final Logger log = LoggerFactory.getLogger(ListOwnershipsResource.class);

    private static final String ENTITY_NAME = "listOwnerships";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListOwnershipsRepository listOwnershipsRepository;

    public ListOwnershipsResource(ListOwnershipsRepository listOwnershipsRepository) {
        this.listOwnershipsRepository = listOwnershipsRepository;
    }

    /**
     * {@code POST  /list-ownerships} : Create a new listOwnerships.
     *
     * @param listOwnerships the listOwnerships to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listOwnerships, or with status {@code 400 (Bad Request)} if the listOwnerships has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-ownerships")
    public ResponseEntity<ListOwnerships> createListOwnerships(@Valid @RequestBody ListOwnerships listOwnerships) throws URISyntaxException {
        log.debug("REST request to save ListOwnerships : {}", listOwnerships);
        if (listOwnerships.getId() != null) {
            throw new BadRequestAlertException("A new listOwnerships cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListOwnerships result = listOwnershipsRepository.save(listOwnerships);
        return ResponseEntity.created(new URI("/api/list-ownerships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-ownerships} : Updates an existing listOwnerships.
     *
     * @param listOwnerships the listOwnerships to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listOwnerships,
     * or with status {@code 400 (Bad Request)} if the listOwnerships is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listOwnerships couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-ownerships")
    public ResponseEntity<ListOwnerships> updateListOwnerships(@Valid @RequestBody ListOwnerships listOwnerships) throws URISyntaxException {
        log.debug("REST request to update ListOwnerships : {}", listOwnerships);
        if (listOwnerships.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListOwnerships result = listOwnershipsRepository.save(listOwnerships);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listOwnerships.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-ownerships} : get all the listOwnerships.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listOwnerships in body.
     */
    @GetMapping("/list-ownerships")
    public List<ListOwnerships> getAllListOwnerships() {
        log.debug("REST request to get all ListOwnerships");
        return listOwnershipsRepository.findAll();
    }

    /**
     * {@code GET  /list-ownerships/:id} : get the "id" listOwnerships.
     *
     * @param id the id of the listOwnerships to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listOwnerships, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-ownerships/{id}")
    public ResponseEntity<ListOwnerships> getListOwnerships(@PathVariable Long id) {
        log.debug("REST request to get ListOwnerships : {}", id);
        Optional<ListOwnerships> listOwnerships = listOwnershipsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listOwnerships);
    }

    /**
     * {@code DELETE  /list-ownerships/:id} : delete the "id" listOwnerships.
     *
     * @param id the id of the listOwnerships to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-ownerships/{id}")
    public ResponseEntity<Void> deleteListOwnerships(@PathVariable Long id) {
        log.debug("REST request to delete ListOwnerships : {}", id);
        listOwnershipsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
