package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.GlobalGroups;
import fr.ippon.kompetitors.domain.Offices;
import fr.ippon.kompetitors.repository.GlobalGroupsRepository;
import fr.ippon.kompetitors.repository.OfficesRepository;
import fr.ippon.kompetitors.service.dto.GroupSearchDTO;
import fr.ippon.kompetitors.service.dto.OfficeSearchDTO;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.GlobalGroups}.
 */
@RestController
@RequestMapping("/api")
public class GlobalGroupsResource {

    private final Logger log = LoggerFactory.getLogger(GlobalGroupsResource.class);

    private static final String ENTITY_NAME = "globalGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GlobalGroupsRepository globalGroupsRepository;
    private final OfficesRepository officesRepository;

    public GlobalGroupsResource(GlobalGroupsRepository globalGroupsRepository, OfficesRepository officesRepository) {
        this.globalGroupsRepository = globalGroupsRepository;
        this.officesRepository = officesRepository;
    }

    /**
     * {@code POST  /global-groups} : Create a new globalGroups.
     *
     * @param globalGroups the globalGroups to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new globalGroups, or with status {@code 400 (Bad Request)} if the globalGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/global-groups")
    public ResponseEntity<GlobalGroups> createGlobalGroups(@Valid @RequestBody GlobalGroups globalGroups) throws URISyntaxException {
        log.debug("REST request to save GlobalGroups : {}", globalGroups);
        if (globalGroups.getId() != null) {
            throw new BadRequestAlertException("A new globalGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlobalGroups result = globalGroupsRepository.save(globalGroups);
        return ResponseEntity.created(new URI("/api/global-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /global-groups} : Updates an existing globalGroups.
     *
     * @param globalGroups the globalGroups to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated globalGroups,
     * or with status {@code 400 (Bad Request)} if the globalGroups is not valid,
     * or with status {@code 500 (Internal Server Error)} if the globalGroups couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/global-groups")
    public ResponseEntity<GlobalGroups> updateGlobalGroups(@Valid @RequestBody GlobalGroups globalGroups) throws URISyntaxException {
        log.debug("REST request to update GlobalGroups : {}", globalGroups);
        if (globalGroups.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GlobalGroups result = globalGroupsRepository.save(globalGroups);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, globalGroups.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /global-groups} : get all the globalGroups.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of globalGroups in body.
     */
    @GetMapping("/global-groups")
    public List<GlobalGroups> getAllGlobalGroups() {
        log.debug("REST request to get all GlobalGroups");
        return globalGroupsRepository.findAll();
    }

    /**
     * {@code GET  /global-groups/:id} : get the "id" globalGroups.
     *
     * @param id the id of the globalGroups to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the globalGroups, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/global-groups/{id}")
    public ResponseEntity<GlobalGroups> getGlobalGroups(@PathVariable Long id) {
        log.debug("REST request to get GlobalGroups : {}", id);
        Optional<GlobalGroups> globalGroups = globalGroupsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(globalGroups);
    }

    /**
     * {@code DELETE  /global-groups/:id} : delete the "id" globalGroups.
     *
     * @param id the id of the globalGroups to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/global-groups/{id}")
    public ResponseEntity<Void> deleteGlobalGroups(@PathVariable Long id) {
        log.debug("REST request to delete GlobalGroups : {}", id);
        globalGroupsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/global-groups/find/")
    public List<GroupSearchDTO> getGroups(@RequestParam String what, @RequestParam String where, @RequestParam String region, @RequestParam String country) {
        log.debug("REST request to find Groups");
        return globalGroupsRepository.findByWhatAndWhereAndRegion(what.toLowerCase(), where.toLowerCase(), region, country);
    }
}
