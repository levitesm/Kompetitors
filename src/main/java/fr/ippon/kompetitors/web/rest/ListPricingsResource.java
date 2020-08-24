package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ListPricings;
import fr.ippon.kompetitors.repository.ListPricingsRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListPricings}.
 */
@RestController
@RequestMapping("/api")
public class ListPricingsResource {

    private final Logger log = LoggerFactory.getLogger(ListPricingsResource.class);

    private static final String ENTITY_NAME = "listPricings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListPricingsRepository listPricingsRepository;

    public ListPricingsResource(ListPricingsRepository listPricingsRepository) {
        this.listPricingsRepository = listPricingsRepository;
    }

    /**
     * {@code POST  /list-pricings} : Create a new listPricings.
     *
     * @param listPricings the listPricings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listPricings, or with status {@code 400 (Bad Request)} if the listPricings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-pricings")
    public ResponseEntity<ListPricings> createListPricings(@Valid @RequestBody ListPricings listPricings) throws URISyntaxException {
        log.debug("REST request to save ListPricings : {}", listPricings);
        if (listPricings.getId() != null) {
            throw new BadRequestAlertException("A new listPricings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListPricings result = listPricingsRepository.save(listPricings);
        return ResponseEntity.created(new URI("/api/list-pricings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-pricings} : Updates an existing listPricings.
     *
     * @param listPricings the listPricings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listPricings,
     * or with status {@code 400 (Bad Request)} if the listPricings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listPricings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-pricings")
    public ResponseEntity<ListPricings> updateListPricings(@Valid @RequestBody ListPricings listPricings) throws URISyntaxException {
        log.debug("REST request to update ListPricings : {}", listPricings);
        if (listPricings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListPricings result = listPricingsRepository.save(listPricings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listPricings.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-pricings} : get all the listPricings.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listPricings in body.
     */
    @GetMapping("/list-pricings")
    public List<ListPricings> getAllListPricings() {
        log.debug("REST request to get all ListPricings");
        return listPricingsRepository.findAll();
    }

    /**
     * {@code GET  /list-pricings/:id} : get the "id" listPricings.
     *
     * @param id the id of the listPricings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listPricings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-pricings/{id}")
    public ResponseEntity<ListPricings> getListPricings(@PathVariable Long id) {
        log.debug("REST request to get ListPricings : {}", id);
        Optional<ListPricings> listPricings = listPricingsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listPricings);
    }

    /**
     * {@code DELETE  /list-pricings/:id} : delete the "id" listPricings.
     *
     * @param id the id of the listPricings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-pricings/{id}")
    public ResponseEntity<Void> deleteListPricings(@PathVariable Long id) {
        log.debug("REST request to delete ListPricings : {}", id);
        listPricingsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
