package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.RegionZipList;
import fr.ippon.kompetitors.repository.RegionZipListRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.RegionZipList}.
 */
@RestController
@RequestMapping("/api")
public class RegionZipListResource {

    private final Logger log = LoggerFactory.getLogger(RegionZipListResource.class);

    private static final String ENTITY_NAME = "regionZipList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegionZipListRepository regionZipListRepository;

    public RegionZipListResource(RegionZipListRepository regionZipListRepository) {
        this.regionZipListRepository = regionZipListRepository;
    }

    /**
     * {@code POST  /region-zip-lists} : Create a new regionZipList.
     *
     * @param regionZipList the regionZipList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regionZipList, or with status {@code 400 (Bad Request)} if the regionZipList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/region-zip-lists")
    public ResponseEntity<RegionZipList> createRegionZipList(@RequestBody RegionZipList regionZipList) throws URISyntaxException {
        log.debug("REST request to save RegionZipList : {}", regionZipList);
        if (regionZipList.getId() != null) {
            throw new BadRequestAlertException("A new regionZipList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegionZipList result = regionZipListRepository.save(regionZipList);
        return ResponseEntity.created(new URI("/api/region-zip-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /region-zip-lists} : Updates an existing regionZipList.
     *
     * @param regionZipList the regionZipList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regionZipList,
     * or with status {@code 400 (Bad Request)} if the regionZipList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regionZipList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/region-zip-lists")
    public ResponseEntity<RegionZipList> updateRegionZipList(@RequestBody RegionZipList regionZipList) throws URISyntaxException {
        log.debug("REST request to update RegionZipList : {}", regionZipList);
        if (regionZipList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegionZipList result = regionZipListRepository.save(regionZipList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regionZipList.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /region-zip-lists} : get all the regionZipLists.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regionZipLists in body.
     */
    @GetMapping("/region-zip-lists")
    public List<RegionZipList> getAllRegionZipLists() {
        log.debug("REST request to get all RegionZipLists");
        return regionZipListRepository.findAll();
    }

    /**
     * {@code GET  /region-zip-lists/:id} : get the "id" regionZipList.
     *
     * @param id the id of the regionZipList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regionZipList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/region-zip-lists/{id}")
    public ResponseEntity<RegionZipList> getRegionZipList(@PathVariable Long id) {
        log.debug("REST request to get RegionZipList : {}", id);
        Optional<RegionZipList> regionZipList = regionZipListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regionZipList);
    }

    /**
     * {@code DELETE  /region-zip-lists/:id} : delete the "id" regionZipList.
     *
     * @param id the id of the regionZipList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/region-zip-lists/{id}")
    public ResponseEntity<Void> deleteRegionZipList(@PathVariable Long id) {
        log.debug("REST request to delete RegionZipList : {}", id);
        regionZipListRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
