package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ListCities;
import fr.ippon.kompetitors.repository.ListCitiesRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListCities}.
 */
@RestController
@RequestMapping("/api")
public class ListCitiesResource {

    private final Logger log = LoggerFactory.getLogger(ListCitiesResource.class);

    private static final String ENTITY_NAME = "listCities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListCitiesRepository listCitiesRepository;

    public ListCitiesResource(ListCitiesRepository listCitiesRepository) {
        this.listCitiesRepository = listCitiesRepository;
    }

    /**
     * {@code POST  /list-cities} : Create a new listCities.
     *
     * @param listCities the listCities to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listCities, or with status {@code 400 (Bad Request)} if the listCities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-cities")
    public ResponseEntity<ListCities> createListCities(@Valid @RequestBody ListCities listCities) throws URISyntaxException {
        log.debug("REST request to save ListCities : {}", listCities);
        if (listCities.getId() != null) {
            throw new BadRequestAlertException("A new listCities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListCities result = listCitiesRepository.save(listCities);
        return ResponseEntity.created(new URI("/api/list-cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-cities} : Updates an existing listCities.
     *
     * @param listCities the listCities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listCities,
     * or with status {@code 400 (Bad Request)} if the listCities is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listCities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-cities")
    public ResponseEntity<ListCities> updateListCities(@Valid @RequestBody ListCities listCities) throws URISyntaxException {
        log.debug("REST request to update ListCities : {}", listCities);
        if (listCities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListCities result = listCitiesRepository.save(listCities);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listCities.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-cities} : get all the listCities.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listCities in body.
     */
    @GetMapping("/list-cities")
    public List<ListCities> getAllListCities() {
        log.debug("REST request to get all ListCities");
        return listCitiesRepository.findAll();
    }

    /**
     * {@code GET  /list-cities/:id} : get the "id" listCities.
     *
     * @param id the id of the listCities to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listCities, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-cities/{id}")
    public ResponseEntity<ListCities> getListCities(@PathVariable Long id) {
        log.debug("REST request to get ListCities : {}", id);
        Optional<ListCities> listCities = listCitiesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listCities);
    }

    /**
     * {@code DELETE  /list-cities/:id} : delete the "id" listCities.
     *
     * @param id the id of the listCities to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-cities/{id}")
    public ResponseEntity<Void> deleteListCities(@PathVariable Long id) {
        log.debug("REST request to delete ListCities : {}", id);
        listCitiesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
