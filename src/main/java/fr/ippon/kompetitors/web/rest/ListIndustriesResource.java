package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ListIndustries;
import fr.ippon.kompetitors.repository.ListIndustriesRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListIndustries}.
 */
@RestController
@RequestMapping("/api")
public class ListIndustriesResource {

    private final Logger log = LoggerFactory.getLogger(ListIndustriesResource.class);

    private static final String ENTITY_NAME = "listIndustries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListIndustriesRepository listIndustriesRepository;

    public ListIndustriesResource(ListIndustriesRepository listIndustriesRepository) {
        this.listIndustriesRepository = listIndustriesRepository;
    }

    /**
     * {@code POST  /list-industries} : Create a new listIndustries.
     *
     * @param listIndustries the listIndustries to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listIndustries, or with status {@code 400 (Bad Request)} if the listIndustries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-industries")
    public ResponseEntity<ListIndustries> createListIndustries(@Valid @RequestBody ListIndustries listIndustries) throws URISyntaxException {
        log.debug("REST request to save ListIndustries : {}", listIndustries);
        if (listIndustries.getId() != null) {
            throw new BadRequestAlertException("A new listIndustries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListIndustries result = listIndustriesRepository.save(listIndustries);
        return ResponseEntity.created(new URI("/api/list-industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-industries} : Updates an existing listIndustries.
     *
     * @param listIndustries the listIndustries to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listIndustries,
     * or with status {@code 400 (Bad Request)} if the listIndustries is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listIndustries couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-industries")
    public ResponseEntity<ListIndustries> updateListIndustries(@Valid @RequestBody ListIndustries listIndustries) throws URISyntaxException {
        log.debug("REST request to update ListIndustries : {}", listIndustries);
        if (listIndustries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListIndustries result = listIndustriesRepository.save(listIndustries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listIndustries.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-industries} : get all the listIndustries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listIndustries in body.
     */
    @GetMapping("/list-industries")
    public List<ListIndustries> getAllListIndustries() {
        log.debug("REST request to get all ListIndustries");
        return listIndustriesRepository.findAll();
    }

    /**
     * {@code GET  /list-industries/:id} : get the "id" listIndustries.
     *
     * @param id the id of the listIndustries to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listIndustries, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-industries/{id}")
    public ResponseEntity<ListIndustries> getListIndustries(@PathVariable Long id) {
        log.debug("REST request to get ListIndustries : {}", id);
        Optional<ListIndustries> listIndustries = listIndustriesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listIndustries);
    }

    /**
     * {@code DELETE  /list-industries/:id} : delete the "id" listIndustries.
     *
     * @param id the id of the listIndustries to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-industries/{id}")
    public ResponseEntity<Void> deleteListIndustries(@PathVariable Long id) {
        log.debug("REST request to delete ListIndustries : {}", id);
        listIndustriesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
