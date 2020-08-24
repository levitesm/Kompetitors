package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.TechInfoService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.TechInfoDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.TechInfo}.
 */
@RestController
@RequestMapping("/api")
public class TechInfoResource {

    private final Logger log = LoggerFactory.getLogger(TechInfoResource.class);

    private static final String ENTITY_NAME = "techInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechInfoService techInfoService;

    public TechInfoResource(TechInfoService techInfoService) {
        this.techInfoService = techInfoService;
    }

    /**
     * {@code POST  /tech-infos} : Create a new techInfo.
     *
     * @param techInfoDTO the techInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techInfoDTO, or with status {@code 400 (Bad Request)} if the techInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tech-infos")
    public ResponseEntity<TechInfoDTO> createTechInfo(@RequestBody TechInfoDTO techInfoDTO) throws URISyntaxException {
        log.debug("REST request to save TechInfo : {}", techInfoDTO);
        if (techInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new techInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechInfoDTO result = techInfoService.save(techInfoDTO);
        return ResponseEntity.created(new URI("/api/tech-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tech-infos} : Updates an existing techInfo.
     *
     * @param techInfoDTO the techInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techInfoDTO,
     * or with status {@code 400 (Bad Request)} if the techInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tech-infos")
    public ResponseEntity<TechInfoDTO> updateTechInfo(@RequestBody TechInfoDTO techInfoDTO) throws URISyntaxException {
        log.debug("REST request to update TechInfo : {}", techInfoDTO);
        if (techInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechInfoDTO result = techInfoService.save(techInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, techInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tech-infos} : get all the techInfos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techInfos in body.
     */
    @GetMapping("/tech-infos")
    public List<TechInfoDTO> getAllTechInfos() {
        log.debug("REST request to get all TechInfos");
        return techInfoService.findAll();
    }

    /**
     * {@code GET  /tech-infos/:id} : get the "id" techInfo.
     *
     * @param id the id of the techInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tech-infos/{id}")
    public ResponseEntity<TechInfoDTO> getTechInfo(@PathVariable Long id) {
        log.debug("REST request to get TechInfo : {}", id);
        Optional<TechInfoDTO> techInfoDTO = techInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techInfoDTO);
    }

    /**
     * {@code DELETE  /tech-infos/:id} : delete the "id" techInfo.
     *
     * @param id the id of the techInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tech-infos/{id}")
    public ResponseEntity<Void> deleteTechInfo(@PathVariable Long id) {
        log.debug("REST request to delete TechInfo : {}", id);
        techInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /tech-infos/competitor/:id} : get all the techInfos by competitor id.
     *
     * @param id competitor id.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techInfos in body.
     */
    @GetMapping("/tech-infos/competitor/{id}")
    public ResponseEntity<List<TechInfoDTO>> getAllTechInfosByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get all TechInfos by competitor id");
        return ResponseEntity.ok(techInfoService.findAllByCompetitorId(id));
    }
}
