package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.TechPartnersService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.TechPartnersDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.TechPartners}.
 */
@RestController
@RequestMapping("/api")
public class TechPartnersResource {

    private final Logger log = LoggerFactory.getLogger(TechPartnersResource.class);

    private static final String ENTITY_NAME = "techPartners";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechPartnersService techPartnersService;

    public TechPartnersResource(TechPartnersService techPartnersService) {
        this.techPartnersService = techPartnersService;
    }

    /**
     * {@code POST  /tech-partners} : Create a new techPartners.
     *
     * @param techPartnersDTO the techPartnersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techPartnersDTO, or with status {@code 400 (Bad Request)} if the techPartners has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tech-partners")
    public ResponseEntity<TechPartnersDTO> createTechPartners(@RequestBody TechPartnersDTO techPartnersDTO) throws URISyntaxException {
        log.debug("REST request to save TechPartners : {}", techPartnersDTO);
        if (techPartnersDTO.getId() != null) {
            throw new BadRequestAlertException("A new techPartners cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechPartnersDTO result = techPartnersService.save(techPartnersDTO);
        return ResponseEntity.created(new URI("/api/tech-partners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tech-partners} : Updates an existing techPartners.
     *
     * @param techPartnersDTO the techPartnersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techPartnersDTO,
     * or with status {@code 400 (Bad Request)} if the techPartnersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techPartnersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tech-partners")
    public ResponseEntity<TechPartnersDTO> updateTechPartners(@RequestBody TechPartnersDTO techPartnersDTO) throws URISyntaxException {
        log.debug("REST request to update TechPartners : {}", techPartnersDTO);
        if (techPartnersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechPartnersDTO result = techPartnersService.save(techPartnersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, techPartnersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tech-partners} : get all the techPartners.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techPartners in body.
     */
    @GetMapping("/tech-partners")
    public List<TechPartnersDTO> getAllTechPartners() {
        log.debug("REST request to get all TechPartners");
        return techPartnersService.findAll();
    }

    /**
     * {@code GET  /tech-partners/:id} : get the "id" techPartners.
     *
     * @param id the id of the techPartnersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techPartnersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tech-partners/{id}")
    public ResponseEntity<TechPartnersDTO> getTechPartners(@PathVariable Long id) {
        log.debug("REST request to get TechPartners : {}", id);
        Optional<TechPartnersDTO> techPartnersDTO = techPartnersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techPartnersDTO);
    }

    /**
     * {@code DELETE  /tech-partners/:id} : delete the "id" techPartners.
     *
     * @param id the id of the techPartnersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tech-partners/{id}")
    public ResponseEntity<Void> deleteTechPartners(@PathVariable Long id) {
        log.debug("REST request to delete TechPartners : {}", id);
        techPartnersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /tech-partners/competitor/{id}} : get all the techPartners by competitor id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techPartners in body.
     */
    @GetMapping("/tech-partners/competitor/{id}")
    public ResponseEntity<List<TechPartnersDTO>> getAllTechPartnersByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get all TechPartners by competitor id {}", id);
        return ResponseEntity.ok(techPartnersService.findAllByCompetitorId(id));
    }

    /**
     * {@code POST  /tech-partners/competitor} : refresh techPartners.
     *
     * @param list of entities to refresh.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of created techPartners in body.
     */
    @PostMapping("/tech-partners/competitor")
    public ResponseEntity<List<TechPartnersDTO>> refreshTechPartners(@RequestBody List<TechPartnersDTO> list) {
        log.debug("REST request to refresh TechPartners: {}", list);
        List<TechPartnersDTO> result = new ArrayList<>();
        list.forEach(dto -> {
            if (dto.getId() == null) {
                result.add(techPartnersService.save(dto));
            } else {
                if (techPartnersService.findOne(dto.getId()).isPresent()) {
                    techPartnersService.delete(dto.getId());
                }
            }
        });
        return ResponseEntity.ok(result);
    }
}
