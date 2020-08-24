package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ListClientsProjectTypes;
import fr.ippon.kompetitors.repository.ListClientsProjectTypesRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListClientsProjectTypes}.
 */
@RestController
@RequestMapping("/api")
public class ListClientsProjectTypesResource {

    private final Logger log = LoggerFactory.getLogger(ListClientsProjectTypesResource.class);

    private static final String ENTITY_NAME = "listClientsProjectTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListClientsProjectTypesRepository listClientsProjectTypesRepository;

    public ListClientsProjectTypesResource(ListClientsProjectTypesRepository listClientsProjectTypesRepository) {
        this.listClientsProjectTypesRepository = listClientsProjectTypesRepository;
    }

    /**
     * {@code POST  /list-clients-project-types} : Create a new listClientsProjectTypes.
     *
     * @param listClientsProjectTypes the listClientsProjectTypes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listClientsProjectTypes, or with status {@code 400 (Bad Request)} if the listClientsProjectTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-clients-project-types")
    public ResponseEntity<ListClientsProjectTypes> createListClientsProjectTypes(@Valid @RequestBody ListClientsProjectTypes listClientsProjectTypes) throws URISyntaxException {
        log.debug("REST request to save ListClientsProjectTypes : {}", listClientsProjectTypes);
        if (listClientsProjectTypes.getId() != null) {
            throw new BadRequestAlertException("A new listClientsProjectTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListClientsProjectTypes result = listClientsProjectTypesRepository.save(listClientsProjectTypes);
        return ResponseEntity.created(new URI("/api/list-clients-project-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-clients-project-types} : Updates an existing listClientsProjectTypes.
     *
     * @param listClientsProjectTypes the listClientsProjectTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listClientsProjectTypes,
     * or with status {@code 400 (Bad Request)} if the listClientsProjectTypes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listClientsProjectTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-clients-project-types")
    public ResponseEntity<ListClientsProjectTypes> updateListClientsProjectTypes(@Valid @RequestBody ListClientsProjectTypes listClientsProjectTypes) throws URISyntaxException {
        log.debug("REST request to update ListClientsProjectTypes : {}", listClientsProjectTypes);
        if (listClientsProjectTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListClientsProjectTypes result = listClientsProjectTypesRepository.save(listClientsProjectTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listClientsProjectTypes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-clients-project-types} : get all the listClientsProjectTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listClientsProjectTypes in body.
     */
    @GetMapping("/list-clients-project-types")
    public List<ListClientsProjectTypes> getAllListClientsProjectTypes() {
        log.debug("REST request to get all ListClientsProjectTypes");
        return listClientsProjectTypesRepository.findAll();
    }

    /**
     * {@code GET  /list-clients-project-types/:id} : get the "id" listClientsProjectTypes.
     *
     * @param id the id of the listClientsProjectTypes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listClientsProjectTypes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-clients-project-types/{id}")
    public ResponseEntity<ListClientsProjectTypes> getListClientsProjectTypes(@PathVariable Long id) {
        log.debug("REST request to get ListClientsProjectTypes : {}", id);
        Optional<ListClientsProjectTypes> listClientsProjectTypes = listClientsProjectTypesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listClientsProjectTypes);
    }

    /**
     * {@code DELETE  /list-clients-project-types/:id} : delete the "id" listClientsProjectTypes.
     *
     * @param id the id of the listClientsProjectTypes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-clients-project-types/{id}")
    public ResponseEntity<Void> deleteListClientsProjectTypes(@PathVariable Long id) {
        log.debug("REST request to delete ListClientsProjectTypes : {}", id);
        listClientsProjectTypesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
