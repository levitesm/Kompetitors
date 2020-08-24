package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.TechCompetanciesService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.TechCompetanciesDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.TechCompetancies}.
 */
@RestController
@RequestMapping("/api")
public class TechCompetanciesResource {

    private final Logger log = LoggerFactory.getLogger(TechCompetanciesResource.class);

    private static final String ENTITY_NAME = "techCompetancies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechCompetanciesService techCompetanciesService;

    public TechCompetanciesResource(TechCompetanciesService techCompetanciesService) {
        this.techCompetanciesService = techCompetanciesService;
    }

    /**
     * {@code POST  /tech-competancies} : Create a new techCompetancies.
     *
     * @param techCompetanciesDTO the techCompetanciesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techCompetanciesDTO, or with status {@code 400 (Bad Request)} if the techCompetancies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tech-competancies")
    public ResponseEntity<TechCompetanciesDTO> createTechCompetancies(@RequestBody TechCompetanciesDTO techCompetanciesDTO) throws URISyntaxException {
        log.debug("REST request to save TechCompetancies : {}", techCompetanciesDTO);
        if (techCompetanciesDTO.getId() != null) {
            throw new BadRequestAlertException("A new techCompetancies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechCompetanciesDTO result = techCompetanciesService.save(techCompetanciesDTO);
        return ResponseEntity.created(new URI("/api/tech-competancies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tech-competancies} : Updates an existing techCompetancies.
     *
     * @param techCompetanciesDTO the techCompetanciesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techCompetanciesDTO,
     * or with status {@code 400 (Bad Request)} if the techCompetanciesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techCompetanciesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tech-competancies")
    public ResponseEntity<TechCompetanciesDTO> updateTechCompetancies(@RequestBody TechCompetanciesDTO techCompetanciesDTO) throws URISyntaxException {
        log.debug("REST request to update TechCompetancies : {}", techCompetanciesDTO);
        if (techCompetanciesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechCompetanciesDTO result = techCompetanciesService.save(techCompetanciesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, techCompetanciesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tech-competancies} : get all the techCompetancies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techCompetancies in body.
     */
    @GetMapping("/tech-competancies")
    public List<TechCompetanciesDTO> getAllTechCompetancies() {
        log.debug("REST request to get all TechCompetancies");
        return techCompetanciesService.findAll();
    }

    /**
     * {@code GET  /tech-competancies/:id} : get the "id" techCompetancies.
     *
     * @param id the id of the techCompetanciesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techCompetanciesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tech-competancies/{id}")
    public ResponseEntity<TechCompetanciesDTO> getTechCompetancies(@PathVariable Long id) {
        log.debug("REST request to get TechCompetancies : {}", id);
        Optional<TechCompetanciesDTO> techCompetanciesDTO = techCompetanciesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techCompetanciesDTO);
    }

    /**
     * {@code DELETE  /tech-competancies/:id} : delete the "id" techCompetancies.
     *
     * @param id the id of the techCompetanciesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tech-competancies/{id}")
    public ResponseEntity<Void> deleteTechCompetancies(@PathVariable Long id) {
        log.debug("REST request to delete TechCompetancies : {}", id);
        techCompetanciesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /tech-competancies/competitor/{id}} : get all the techCompetancies by competitor id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techCompetancies in body.
     */
    @GetMapping("/tech-competancies/competitor/{id}")
    public ResponseEntity<List<TechCompetanciesDTO>> getAllTechCompetanciesByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get all TechCompetancies by competitor id {}", id);
        return ResponseEntity.ok(techCompetanciesService.findAllByCompetitorId(id));
    }

    /**
     * {@code POST  /tech-competancies/competitor} : refresh techCompetancies.
     *
     * @param list of entities to refresh.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of created techCompetancies in body.
     */
    @PostMapping("/tech-competancies/competitor")
    public ResponseEntity<List<TechCompetanciesDTO>> refreshTechCompetancies(@RequestBody List<TechCompetanciesDTO> list) {
        log.debug("REST request to refresh TechCompetancies: {}", list);
        List<TechCompetanciesDTO> result = new ArrayList<>();
        list.forEach(dto -> {
            if (dto.getId() == null) {
                result.add(techCompetanciesService.save(dto));
            } else {
                if (techCompetanciesService.findOne(dto.getId()).isPresent()) {
                    techCompetanciesService.delete(dto.getId());
                }
            }
        });
        return ResponseEntity.ok(result);
    }
}
