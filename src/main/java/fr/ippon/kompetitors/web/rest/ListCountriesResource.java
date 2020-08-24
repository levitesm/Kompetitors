package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ListCountries;
import fr.ippon.kompetitors.repository.ListCountriesRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListCountries}.
 */
@RestController
@RequestMapping("/api")
public class ListCountriesResource {

    private final Logger log = LoggerFactory.getLogger(ListCountriesResource.class);

    private static final String ENTITY_NAME = "listCountries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListCountriesRepository listCountriesRepository;

    public ListCountriesResource(ListCountriesRepository listCountriesRepository) {
        this.listCountriesRepository = listCountriesRepository;
    }

    /**
     * {@code POST  /list-countries} : Create a new listCountries.
     *
     * @param listCountries the listCountries to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listCountries, or with status {@code 400 (Bad Request)} if the listCountries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-countries")
    public ResponseEntity<ListCountries> createListCountries(@Valid @RequestBody ListCountries listCountries) throws URISyntaxException {
        log.debug("REST request to save ListCountries : {}", listCountries);
        if (listCountries.getId() != null) {
            throw new BadRequestAlertException("A new listCountries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListCountries result = listCountriesRepository.save(listCountries);
        return ResponseEntity.created(new URI("/api/list-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-countries} : Updates an existing listCountries.
     *
     * @param listCountries the listCountries to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listCountries,
     * or with status {@code 400 (Bad Request)} if the listCountries is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listCountries couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-countries")
    public ResponseEntity<ListCountries> updateListCountries(@Valid @RequestBody ListCountries listCountries) throws URISyntaxException {
        log.debug("REST request to update ListCountries : {}", listCountries);
        if (listCountries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListCountries result = listCountriesRepository.save(listCountries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listCountries.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-countries} : get all the listCountries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listCountries in body.
     */
    @GetMapping("/list-countries")
    public List<ListCountries> getAllListCountries() {
        log.debug("REST request to get all ListCountries");
        return listCountriesRepository.findAll();
    }

    /**
     * {@code GET  /list-countries/:id} : get the "id" listCountries.
     *
     * @param id the id of the listCountries to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listCountries, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-countries/{id}")
    public ResponseEntity<ListCountries> getListCountries(@PathVariable Long id) {
        log.debug("REST request to get ListCountries : {}", id);
        Optional<ListCountries> listCountries = listCountriesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listCountries);
    }

    /**
     * {@code DELETE  /list-countries/:id} : delete the "id" listCountries.
     *
     * @param id the id of the listCountries to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-countries/{id}")
    public ResponseEntity<Void> deleteListCountries(@PathVariable Long id) {
        log.debug("REST request to delete ListCountries : {}", id);
        listCountriesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
