package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.Representatives;
import fr.ippon.kompetitors.repository.RepresentativesRepository;
import fr.ippon.kompetitors.service.InfogreffeService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.Representatives}.
 */
@RestController
@RequestMapping("/api")
public class RepresentativesResource {

    private final Logger log = LoggerFactory.getLogger(RepresentativesResource.class);

    private static final String ENTITY_NAME = "representatives";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepresentativesRepository representativesRepository;

    private final InfogreffeService infogreffeService;

    public RepresentativesResource(RepresentativesRepository representativesRepository, InfogreffeService infogreffeService) {
        this.representativesRepository = representativesRepository;
        this.infogreffeService = infogreffeService;
    }

    /**
     * {@code POST  /representatives} : Create a new representatives.
     *
     * @param representatives the representatives to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new representatives, or with status {@code 400 (Bad Request)} if the representatives has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/representatives")
    public ResponseEntity<Representatives> createRepresentatives(@RequestBody Representatives representatives) throws URISyntaxException {
        log.debug("REST request to save Representatives : {}", representatives);
        if (representatives.getId() != null) {
            throw new BadRequestAlertException("A new representatives cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Representatives result = representativesRepository.save(representatives);
        return ResponseEntity.created(new URI("/api/representatives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /representatives} : Updates an existing representatives.
     *
     * @param representatives the representatives to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representatives,
     * or with status {@code 400 (Bad Request)} if the representatives is not valid,
     * or with status {@code 500 (Internal Server Error)} if the representatives couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/representatives")
    public ResponseEntity<Representatives> updateRepresentatives(@RequestBody Representatives representatives) throws URISyntaxException {
        log.debug("REST request to update Representatives : {}", representatives);
        if (representatives.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Representatives result = representativesRepository.save(representatives);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, representatives.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /representatives} : get all the representatives.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of representatives in body.
     */
    @GetMapping("/representatives")
    public List<Representatives> getAllRepresentatives() {
        log.debug("REST request to get all Representatives");
        return representativesRepository.findAll();
    }

    /**
     * {@code GET  /representatives/:id} : get the "id" representatives.
     *
     * @param id the id of the representatives to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the representatives, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/representatives/{id}")
    public ResponseEntity<Representatives> getRepresentatives(@PathVariable Long id) {
        log.debug("REST request to get Representatives : {}", id);
        Optional<Representatives> representatives = representativesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(representatives);
    }

    /**
     * {@code DELETE  /representatives/:id} : delete the "id" representatives.
     *
     * @param id the id of the representatives to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/representatives/{id}")
    public ResponseEntity<Void> deleteRepresentatives(@PathVariable Long id) {
        log.debug("REST request to delete Representatives : {}", id);
        representativesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/representatives/siren/{siren}")
    public List<Representatives> getRepresentativesBySiren(@PathVariable String siren) {
        log.debug("REST request to get Representatives by SIREN : {}", siren);
        List<String> sirenList = new ArrayList<>();
        sirenList.add(siren);
        return representativesRepository.findAllByCompetitorSiren(sirenList);
    }

    @PutMapping("/representatives/siren/{siren}")
    public void updateRepresentativesBySiren(@PathVariable String siren) {
        log.debug("REST request to UPDATE Representatives by SIREN : {}", siren);
        infogreffeService.updateRepresentatives(siren);
    }
}
