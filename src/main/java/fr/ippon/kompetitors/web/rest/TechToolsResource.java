package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.TechToolsService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.TechToolsDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.TechTools}.
 */
@RestController
@RequestMapping("/api")
public class TechToolsResource {

    private final Logger log = LoggerFactory.getLogger(TechToolsResource.class);

    private static final String ENTITY_NAME = "techTools";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechToolsService techToolsService;

    public TechToolsResource(TechToolsService techToolsService) {
        this.techToolsService = techToolsService;
    }

    /**
     * {@code POST  /tech-tools} : Create a new techTools.
     *
     * @param techToolsDTO the techToolsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techToolsDTO, or with status {@code 400 (Bad Request)} if the techTools has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tech-tools")
    public ResponseEntity<TechToolsDTO> createTechTools(@RequestBody TechToolsDTO techToolsDTO) throws URISyntaxException {
        log.debug("REST request to save TechTools : {}", techToolsDTO);
        if (techToolsDTO.getId() != null) {
            throw new BadRequestAlertException("A new techTools cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechToolsDTO result = techToolsService.save(techToolsDTO);
        return ResponseEntity.created(new URI("/api/tech-tools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tech-tools} : Updates an existing techTools.
     *
     * @param techToolsDTO the techToolsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techToolsDTO,
     * or with status {@code 400 (Bad Request)} if the techToolsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techToolsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tech-tools")
    public ResponseEntity<TechToolsDTO> updateTechTools(@RequestBody TechToolsDTO techToolsDTO) throws URISyntaxException {
        log.debug("REST request to update TechTools : {}", techToolsDTO);
        if (techToolsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechToolsDTO result = techToolsService.save(techToolsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, techToolsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tech-tools} : get all the techTools.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techTools in body.
     */
    @GetMapping("/tech-tools")
    public List<TechToolsDTO> getAllTechTools() {
        log.debug("REST request to get all TechTools");
        return techToolsService.findAll();
    }

    /**
     * {@code GET  /tech-tools/:id} : get the "id" techTools.
     *
     * @param id the id of the techToolsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techToolsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tech-tools/{id}")
    public ResponseEntity<TechToolsDTO> getTechTools(@PathVariable Long id) {
        log.debug("REST request to get TechTools : {}", id);
        Optional<TechToolsDTO> techToolsDTO = techToolsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techToolsDTO);
    }

    /**
     * {@code DELETE  /tech-tools/:id} : delete the "id" techTools.
     *
     * @param id the id of the techToolsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tech-tools/{id}")
    public ResponseEntity<Void> deleteTechTools(@PathVariable Long id) {
        log.debug("REST request to delete TechTools : {}", id);
        techToolsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /tech-tools/competitor/{id}} : get all the techTools by competitor id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techTools in body.
     */
    @GetMapping("/tech-tools/competitor/{id}")
    public ResponseEntity<List<TechToolsDTO>> getAllTechToolsByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get all TechTools by competitor id {}", id);
        return ResponseEntity.ok(techToolsService.findAllByCompetitorId(id));
    }

    /**
     * {@code POST  /tech-tools/competitor} : refresh techTools.
     *
     * @param list of entities to refresh.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of created techTools in body.
     */
    @PostMapping("/tech-tools/competitor")
    public ResponseEntity<List<TechToolsDTO>> refreshTechServices(@RequestBody List<TechToolsDTO> list) {
        log.debug("REST request to refresh TechTools: {}", list);
        List<TechToolsDTO> result = new ArrayList<>();
        list.forEach(dto -> {
            if (dto.getId() == null) {
                result.add(techToolsService.save(dto));
            } else {
                if (techToolsService.findOne(dto.getId()).isPresent()) {
                    techToolsService.delete(dto.getId());
                }
            }
        });
        return ResponseEntity.ok(result);
    }
}
