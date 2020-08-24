package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ListCityCountries;
import fr.ippon.kompetitors.repository.ListCityCountriesRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListCityCountries}.
 */
@RestController
@RequestMapping("/api")
public class ListCityCountriesResource {

    private final Logger log = LoggerFactory.getLogger(ListCityCountriesResource.class);

    private static final String ENTITY_NAME = "listCityCountries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListCityCountriesRepository listCityCountriesRepository;

    public ListCityCountriesResource(ListCityCountriesRepository listCityCountriesRepository) {
        this.listCityCountriesRepository = listCityCountriesRepository;
    }

    /**
     * {@code POST  /list-city-countries} : Create a new listCityCountries.
     *
     * @param listCityCountries the listCityCountries to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listCityCountries, or with status {@code 400 (Bad Request)} if the listCityCountries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-city-countries")
    public ResponseEntity<ListCityCountries> createListCityCountries(@Valid @RequestBody ListCityCountries listCityCountries) throws URISyntaxException {
        log.debug("REST request to save ListCityCountries : {}", listCityCountries);
        if (listCityCountries.getId() != null) {
            throw new BadRequestAlertException("A new listCityCountries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListCityCountries result = listCityCountriesRepository.save(listCityCountries);
        return ResponseEntity.created(new URI("/api/list-city-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-city-countries} : Updates an existing listCityCountries.
     *
     * @param listCityCountries the listCityCountries to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listCityCountries,
     * or with status {@code 400 (Bad Request)} if the listCityCountries is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listCityCountries couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-city-countries")
    public ResponseEntity<ListCityCountries> updateListCityCountries(@Valid @RequestBody ListCityCountries listCityCountries) throws URISyntaxException {
        log.debug("REST request to update ListCityCountries : {}", listCityCountries);
        if (listCityCountries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListCityCountries result = listCityCountriesRepository.save(listCityCountries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listCityCountries.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-city-countries} : get all the listCityCountries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listCityCountries in body.
     */
    @GetMapping("/list-city-countries")
    public List<ListCityCountries> getAllListCityCountries() {
        log.debug("REST request to get all ListCityCountries");
        return listCityCountriesRepository.findAll();
    }

    /**
     * {@code GET  /list-city-countries/:id} : get the "id" listCityCountries.
     *
     * @param id the id of the listCityCountries to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listCityCountries, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-city-countries/{id}")
    public ResponseEntity<ListCityCountries> getListCityCountries(@PathVariable Long id) {
        log.debug("REST request to get ListCityCountries : {}", id);
        Optional<ListCityCountries> listCityCountries = listCityCountriesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listCityCountries);
    }

    /**
     * {@code DELETE  /list-city-countries/:id} : delete the "id" listCityCountries.
     *
     * @param id the id of the listCityCountries to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-city-countries/{id}")
    public ResponseEntity<Void> deleteListCityCountries(@PathVariable Long id) {
        log.debug("REST request to delete ListCityCountries : {}", id);
        listCityCountriesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
