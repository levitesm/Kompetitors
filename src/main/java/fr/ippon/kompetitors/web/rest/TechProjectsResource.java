package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.TechProjectsService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.TechProjectsDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.TechProjects}.
 */
@RestController
@RequestMapping("/api")
public class TechProjectsResource {

    private final Logger log = LoggerFactory.getLogger(TechProjectsResource.class);

    private static final String ENTITY_NAME = "techProjects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechProjectsService techProjectsService;

    public TechProjectsResource(TechProjectsService techProjectsService) {
        this.techProjectsService = techProjectsService;
    }

    /**
     * {@code POST  /tech-projects} : Create a new techProjects.
     *
     * @param techProjectsDTO the techProjectsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techProjectsDTO, or with status {@code 400 (Bad Request)} if the techProjects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tech-projects")
    public ResponseEntity<TechProjectsDTO> createTechProjects(@RequestBody TechProjectsDTO techProjectsDTO) throws URISyntaxException {
        log.debug("REST request to save TechProjects : {}", techProjectsDTO);
        if (techProjectsDTO.getId() != null) {
            throw new BadRequestAlertException("A new techProjects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechProjectsDTO result = techProjectsService.save(techProjectsDTO);
        return ResponseEntity.created(new URI("/api/tech-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tech-projects} : Updates an existing techProjects.
     *
     * @param techProjectsDTO the techProjectsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techProjectsDTO,
     * or with status {@code 400 (Bad Request)} if the techProjectsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techProjectsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tech-projects")
    public ResponseEntity<TechProjectsDTO> updateTechProjects(@RequestBody TechProjectsDTO techProjectsDTO) throws URISyntaxException {
        log.debug("REST request to update TechProjects : {}", techProjectsDTO);
        if (techProjectsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechProjectsDTO result = techProjectsService.save(techProjectsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, techProjectsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tech-projects} : get all the techProjects.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techProjects in body.
     */
    @GetMapping("/tech-projects")
    public List<TechProjectsDTO> getAllTechProjects() {
        log.debug("REST request to get all TechProjects");
        return techProjectsService.findAll();
    }

    /**
     * {@code GET  /tech-projects/:id} : get the "id" techProjects.
     *
     * @param id the id of the techProjectsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techProjectsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tech-projects/{id}")
    public ResponseEntity<TechProjectsDTO> getTechProjects(@PathVariable Long id) {
        log.debug("REST request to get TechProjects : {}", id);
        Optional<TechProjectsDTO> techProjectsDTO = techProjectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techProjectsDTO);
    }

    /**
     * {@code DELETE  /tech-projects/:id} : delete the "id" techProjects.
     *
     * @param id the id of the techProjectsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tech-projects/{id}")
    public ResponseEntity<Void> deleteTechProjects(@PathVariable Long id) {
        log.debug("REST request to delete TechProjects : {}", id);
        techProjectsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /tech-projects/competitor/{id}} : get all the techProjects by competitor id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techProjects in body.
     */
    @GetMapping("/tech-projects/competitor/{id}")
    public ResponseEntity<List<TechProjectsDTO>> getAllTechProjectsByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get all TechProjects by competitor id {}", id);
        return ResponseEntity.ok(techProjectsService.findAllByCompetitorId(id));
    }

    /**
     * {@code POST  /tech-projects/competitor} : refresh techProjects.
     *
     * @param list of entities to refresh.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of created techProjects in body.
     */
    @PostMapping("/tech-projects/competitor")
    public ResponseEntity<List<TechProjectsDTO>> refreshTechProjects(@RequestBody List<TechProjectsDTO> list) {
        log.debug("REST request to refresh TechProjects: {}", list);
        List<TechProjectsDTO> result = new ArrayList<>();
        list.forEach(dto -> {
            if (dto.getId() == null) {
                result.add(techProjectsService.save(dto));
            } else {
                if (techProjectsService.findOne(dto.getId()).isPresent()) {
                    techProjectsService.delete(dto.getId());
                }
            }
        });
        return ResponseEntity.ok(result);
    }
}
