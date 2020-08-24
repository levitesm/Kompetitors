package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.Offices;
import fr.ippon.kompetitors.repository.OfficesRepository;
import fr.ippon.kompetitors.service.GeoCodingService;
import fr.ippon.kompetitors.service.dto.OfficeSearchDTO;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.Offices}.
 */
@RestController
@RequestMapping("/api")
public class OfficesResource {

    private final Logger log = LoggerFactory.getLogger(OfficesResource.class);

    private static final String ENTITY_NAME = "offices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficesRepository officesRepository;
    private final GeoCodingService geoCodingService;

    public OfficesResource(OfficesRepository officesRepository, GeoCodingService geoCodingService) {
        this.officesRepository = officesRepository;
        this.geoCodingService = geoCodingService;
    }

    /**
     * {@code POST  /offices} : Create a new offices.
     *
     * @param offices the offices to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offices, or with status {@code 400 (Bad Request)} if the offices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offices")
    public ResponseEntity<Offices> createOffices(@RequestBody Offices offices) throws URISyntaxException {
        log.debug("REST request to save Offices : {}", offices);
        if (offices.getId() != null) {
            throw new BadRequestAlertException("A new offices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        offices = this.geoCodingService.getCoordinates(offices);
        Offices result = officesRepository.save(offices);
        return ResponseEntity.created(new URI("/api/offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offices} : Updates an existing offices.
     *
     * @param offices the offices to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offices,
     * or with status {@code 400 (Bad Request)} if the offices is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offices couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offices")
    public ResponseEntity<Offices> updateOffices(@RequestBody Offices offices) throws URISyntaxException {
        log.debug("REST request to update Offices : {}", offices);
        if (offices.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        offices = this.geoCodingService.getCoordinates(offices);
        Offices result = officesRepository.save(offices);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offices.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offices} : get all the offices.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offices in body.
     */
    @GetMapping("/offices")
    public List<Offices> getAllOffices() {
        log.debug("REST request to get all Offices");
        return officesRepository.findAll();
    }

    /**
     * {@code GET  /offices/:id} : get the "id" offices.
     *
     * @param id the id of the offices to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offices, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offices/{id}")
    public ResponseEntity<Offices> getOffices(@PathVariable Long id) {
        log.debug("REST request to get Offices : {}", id);
        Optional<Offices> offices = officesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(offices);
    }

    /**
     * {@code DELETE  /offices/:id} : delete the "id" offices.
     *
     * @param id the id of the offices to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offices/{id}")
    public ResponseEntity<Void> deleteOffices(@PathVariable Long id) {
        log.debug("REST request to delete Offices : {}", id);
        officesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /offices/find} : find offices by params.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offices in body.
     */
    @GetMapping("/offices/find")
    public ResponseEntity<List<OfficeSearchDTO>> findByNameAndCityAndRegionAndCountry(@RequestParam String what, @RequestParam String where, @RequestParam String region, @RequestParam String country) {
        log.debug("REST request to find Offices: Name: {}, City: {}, Region: {}, Country: {}", what.toLowerCase(), where.toLowerCase(), region, country);
        return ResponseEntity.ok(officesRepository.findByNameAndCityAndRegionAndCountry(what.toLowerCase(), where.toLowerCase(), region, country));
    }

    /**
     * {@code GET  /offices/update-coordinates} : update offices empty coordinates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offices in body.
     */
    @GetMapping("/offices/update-coordinates")
    public ResponseEntity updateOfficesEmptyCoordinates() {
        log.debug("REST request to update offices empty coordinates");
        this.geoCodingService.updateEmptyCoordinates();
        return ResponseEntity.ok().build();
    }
}
