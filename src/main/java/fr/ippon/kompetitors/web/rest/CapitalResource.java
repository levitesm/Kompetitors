package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.Capital;
import fr.ippon.kompetitors.domain.Representatives;
import fr.ippon.kompetitors.repository.CapitalRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.Capital}.
 */
@RestController
@RequestMapping("/api")
public class CapitalResource {

    private final Logger log = LoggerFactory.getLogger(CapitalResource.class);

    private static final String ENTITY_NAME = "capital";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CapitalRepository capitalRepository;

    public CapitalResource(CapitalRepository capitalRepository) {
        this.capitalRepository = capitalRepository;
    }

    /**
     * {@code POST  /capitals} : Create a new capital.
     *
     * @param capital the capital to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new capital, or with status {@code 400 (Bad Request)} if the capital has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/capitals")
    public ResponseEntity<Capital> createCapital(@RequestBody Capital capital) throws URISyntaxException {
        log.debug("REST request to save Capital : {}", capital);
        if (capital.getId() != null) {
            throw new BadRequestAlertException("A new capital cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Capital result = capitalRepository.save(capital);
        return ResponseEntity.created(new URI("/api/capitals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /capitals} : Updates an existing capital.
     *
     * @param capital the capital to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capital,
     * or with status {@code 400 (Bad Request)} if the capital is not valid,
     * or with status {@code 500 (Internal Server Error)} if the capital couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/capitals")
    public ResponseEntity<Capital> updateCapital(@RequestBody Capital capital) throws URISyntaxException {
        log.debug("REST request to update Capital : {}", capital);
        if (capital.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Capital result = capitalRepository.save(capital);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, capital.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /capitals} : get all the capitals.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of capitals in body.
     */
    @GetMapping("/capitals")
    public List<Capital> getAllCapitals() {
        log.debug("REST request to get all Capitals");
        return capitalRepository.findAll();
    }

    /**
     * {@code GET  /capitals/:id} : get the "id" capital.
     *
     * @param id the id of the capital to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the capital, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/capitals/{id}")
    public ResponseEntity<Capital> getCapital(@PathVariable Long id) {
        log.debug("REST request to get Capital : {}", id);
        Optional<Capital> capital = capitalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(capital);
    }

    /**
     * {@code DELETE  /capitals/:id} : delete the "id" capital.
     *
     * @param id the id of the capital to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/capitals/{id}")
    public ResponseEntity<Void> deleteCapital(@PathVariable Long id) {
        log.debug("REST request to delete Capital : {}", id);
        capitalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/capitals/siren/{siren}")
    public List<Capital> getCapitalBySiren(@PathVariable String siren) {
        log.debug("REST request to get Capital by SIREN : {}", siren);
        List<String> sirenList = new ArrayList<>();
        sirenList.add(siren);
        return capitalRepository.findAllByCompetitorSiren(sirenList);
    }

}
