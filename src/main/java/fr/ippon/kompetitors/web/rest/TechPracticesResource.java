package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.TechPracticesService;
import fr.ippon.kompetitors.service.dto.TechPracticesDTO;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.TechPractices}.
 */
@RestController
@RequestMapping("/api")
public class TechPracticesResource {

    private final Logger log = LoggerFactory.getLogger(TechPracticesResource.class);

    private static final String ENTITY_NAME = "techPractices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechPracticesService techPracticesService;

    public TechPracticesResource(TechPracticesService techPracticesService) {
        this.techPracticesService = techPracticesService;
    }

    /**
     * {@code POST  /tech-practices} : Create a new techPractices.
     *
     * @param techPracticesDTO the techPracticesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techPracticesDTO, or with status {@code 400 (Bad Request)} if the techPractices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tech-practices")
    public ResponseEntity<TechPracticesDTO> createTechPractices(@RequestBody TechPracticesDTO techPracticesDTO) throws URISyntaxException {
        log.debug("REST request to save TechPractices : {}", techPracticesDTO);
        if (techPracticesDTO.getId() != null) {
            throw new BadRequestAlertException("A new techPractices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechPracticesDTO result = techPracticesService.save(techPracticesDTO);
        return ResponseEntity.created(new URI("/api/tech-practices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tech-practices} : Updates an existing techPractices.
     *
     * @param techPracticesDTO the techPracticesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techPracticesDTO,
     * or with status {@code 400 (Bad Request)} if the techPracticesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techPracticesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tech-practices")
    public ResponseEntity<TechPracticesDTO> updateTechPractices(@RequestBody TechPracticesDTO techPracticesDTO) throws URISyntaxException {
        log.debug("REST request to update TechPractices : {}", techPracticesDTO);
        if (techPracticesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechPracticesDTO result = techPracticesService.save(techPracticesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, techPracticesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tech-practices} : get all the techPractices.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techPractices in body.
     */
    @GetMapping("/tech-practices")
    public List<TechPracticesDTO> getAllTechPractices() {
        log.debug("REST request to get all TechPractices");
        return techPracticesService.findAll();
    }

    /**
     * {@code GET  /tech-practices/:id} : get the "id" techPractices.
     *
     * @param id the id of the techPracticesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techPracticesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tech-practices/{id}")
    public ResponseEntity<TechPracticesDTO> getTechPractices(@PathVariable Long id) {
        log.debug("REST request to get TechPractices : {}", id);
        Optional<TechPracticesDTO> techPracticesDTO = techPracticesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techPracticesDTO);
    }

    /**
     * {@code DELETE  /tech-practices/:id} : delete the "id" techPractices.
     *
     * @param id the id of the techPracticesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tech-practices/{id}")
    public ResponseEntity<Void> deleteTechPractices(@PathVariable Long id) {
        log.debug("REST request to delete TechPractices : {}", id);
        techPracticesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /tech-practices/competitor/{id}} : get all the techPractices by competitor id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techPractices in body.
     */
    @GetMapping("/tech-practices/competitor/{id}")
    public ResponseEntity<List<TechPracticesDTO>> getAllTechPracticesByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get all TechPractices by competitor id {}", id);
        return ResponseEntity.ok(techPracticesService.findAllByCompetitorId(id));
    }

    /**
     * {@code POST  /tech-practices/competitor} : refresh techPractices.
     *
     * @param list of entities to refresh.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of created techPractices in body.
     */
    @PostMapping("/tech-practices/competitor")
    public ResponseEntity<List<TechPracticesDTO>> refreshTechPractices(@RequestBody List<TechPracticesDTO> list) {
        log.debug("REST request to refresh TechPractices: {}", list);
        List<TechPracticesDTO> result = new ArrayList<>();
        list.forEach(dto -> {
            if (dto.getId() == null) {
                result.add(techPracticesService.save(dto));
            } else {
                if (techPracticesService.findOne(dto.getId()).isPresent()) {
                    techPracticesService.delete(dto.getId());
                }
            }
        });
        return ResponseEntity.ok(result);
    }
}
