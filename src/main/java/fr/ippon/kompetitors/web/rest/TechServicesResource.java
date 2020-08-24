package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.TechServicesService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.TechServicesDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.TechServices}.
 */
@RestController
@RequestMapping("/api")
public class TechServicesResource {

    private final Logger log = LoggerFactory.getLogger(TechServicesResource.class);

    private static final String ENTITY_NAME = "techServices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechServicesService techServicesService;

    public TechServicesResource(TechServicesService techServicesService) {
        this.techServicesService = techServicesService;
    }

    /**
     * {@code POST  /tech-services} : Create a new techServices.
     *
     * @param techServicesDTO the techServicesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techServicesDTO, or with status {@code 400 (Bad Request)} if the techServices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tech-services")
    public ResponseEntity<TechServicesDTO> createTechServices(@RequestBody TechServicesDTO techServicesDTO) throws URISyntaxException {
        log.debug("REST request to save TechServices : {}", techServicesDTO);
        if (techServicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new techServices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechServicesDTO result = techServicesService.save(techServicesDTO);
        return ResponseEntity.created(new URI("/api/tech-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tech-services} : Updates an existing techServices.
     *
     * @param techServicesDTO the techServicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techServicesDTO,
     * or with status {@code 400 (Bad Request)} if the techServicesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techServicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tech-services")
    public ResponseEntity<TechServicesDTO> updateTechServices(@RequestBody TechServicesDTO techServicesDTO) throws URISyntaxException {
        log.debug("REST request to update TechServices : {}", techServicesDTO);
        if (techServicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechServicesDTO result = techServicesService.save(techServicesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, techServicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tech-services} : get all the techServices.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techServices in body.
     */
    @GetMapping("/tech-services")
    public List<TechServicesDTO> getAllTechServices() {
        log.debug("REST request to get all TechServices");
        return techServicesService.findAll();
    }

    /**
     * {@code GET  /tech-services/:id} : get the "id" techServices.
     *
     * @param id the id of the techServicesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techServicesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tech-services/{id}")
    public ResponseEntity<TechServicesDTO> getTechServices(@PathVariable Long id) {
        log.debug("REST request to get TechServices : {}", id);
        Optional<TechServicesDTO> techServicesDTO = techServicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techServicesDTO);
    }

    /**
     * {@code DELETE  /tech-services/:id} : delete the "id" techServices.
     *
     * @param id the id of the techServicesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tech-services/{id}")
    public ResponseEntity<Void> deleteTechServices(@PathVariable Long id) {
        log.debug("REST request to delete TechServices : {}", id);
        techServicesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /tech-services/competitor/{id}} : get all the techServices by competitor id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techServices in body.
     */
    @GetMapping("/tech-services/competitor/{id}")
    public ResponseEntity<List<TechServicesDTO>> getAllTechServicesByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get all TechServices by competitor id {}", id);
        return ResponseEntity.ok(techServicesService.findAllByCompetitorId(id));
    }

    /**
     * {@code POST  /tech-services/competitor} : refresh techServices.
     *
     * @param list of entities to refresh.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of created techServices in body.
     */
    @PostMapping("/tech-services/competitor")
    public ResponseEntity<List<TechServicesDTO>> refreshTechServices(@RequestBody List<TechServicesDTO> list) {
        log.debug("REST request to refresh TechServices: {}", list);
        List<TechServicesDTO> result = new ArrayList<>();
        list.forEach(dto -> {
            if (dto.getId() == null) {
                result.add(techServicesService.save(dto));
            } else {
                if (techServicesService.findOne(dto.getId()).isPresent()) {
                    techServicesService.delete(dto.getId());
                }
            }
        });
        return ResponseEntity.ok(result);
    }
}
