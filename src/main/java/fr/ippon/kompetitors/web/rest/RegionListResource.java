package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.RegionList;
import fr.ippon.kompetitors.repository.RegionListRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.RegionList}.
 */
@RestController
@RequestMapping("/api")
public class RegionListResource {

    private final Logger log = LoggerFactory.getLogger(RegionListResource.class);

    private static final String ENTITY_NAME = "regionList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegionListRepository regionListRepository;

    public RegionListResource(RegionListRepository regionListRepository) {
        this.regionListRepository = regionListRepository;
    }

    /**
     * {@code POST  /region-lists} : Create a new regionList.
     *
     * @param regionList the regionList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regionList, or with status {@code 400 (Bad Request)} if the regionList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/region-lists")
    public ResponseEntity<RegionList> createRegionList(@RequestBody RegionList regionList) throws URISyntaxException {
        log.debug("REST request to save RegionList : {}", regionList);
        if (regionList.getId() != null) {
            throw new BadRequestAlertException("A new regionList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegionList result = regionListRepository.save(regionList);
        return ResponseEntity.created(new URI("/api/region-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /region-lists} : Updates an existing regionList.
     *
     * @param regionList the regionList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regionList,
     * or with status {@code 400 (Bad Request)} if the regionList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regionList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/region-lists")
    public ResponseEntity<RegionList> updateRegionList(@RequestBody RegionList regionList) throws URISyntaxException {
        log.debug("REST request to update RegionList : {}", regionList);
        if (regionList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegionList result = regionListRepository.save(regionList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regionList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /region-lists} : get all the regionLists.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regionLists in body.
     */
    @GetMapping("/region-lists")
    public List<RegionList> getAllRegionLists() {
        log.debug("REST request to get all RegionLists");
        return regionListRepository.findAll();
    }

    /**
     * {@code GET  /region-lists/:id} : get the "id" regionList.
     *
     * @param id the id of the regionList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regionList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/region-lists/{id}")
    public ResponseEntity<RegionList> getRegionList(@PathVariable Long id) {
        log.debug("REST request to get RegionList : {}", id);
        Optional<RegionList> regionList = regionListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regionList);
    }

    /**
     * {@code DELETE  /region-lists/:id} : delete the "id" regionList.
     *
     * @param id the id of the regionList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/region-lists/{id}")
    public ResponseEntity<Void> deleteRegionList(@PathVariable Long id) {
        log.debug("REST request to delete RegionList : {}", id);
        regionListRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
