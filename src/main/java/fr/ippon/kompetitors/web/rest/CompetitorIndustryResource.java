package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.CompetitorIndustryService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.CompetitorIndustryDTO;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.CompetitorIndustry}.
 */
@RestController
@RequestMapping("/api")
public class CompetitorIndustryResource {

    private final Logger log = LoggerFactory.getLogger(CompetitorIndustryResource.class);

    private static final String ENTITY_NAME = "competitorIndustry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitorIndustryService competitorIndustryService;

    public CompetitorIndustryResource(CompetitorIndustryService competitorIndustryService) {
        this.competitorIndustryService = competitorIndustryService;
    }

    /**
     * {@code POST  /competitor-industries} : Create a new competitorIndustry.
     *
     * @param competitorIndustryDTO the competitorIndustryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitorIndustryDTO, or with status {@code 400 (Bad Request)} if the competitorIndustry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitor-industries")
    public ResponseEntity<CompetitorIndustryDTO> createCompetitorIndustry(@Valid @RequestBody CompetitorIndustryDTO competitorIndustryDTO) throws URISyntaxException {
        log.debug("REST request to save CompetitorIndustry : {}", competitorIndustryDTO);
        if (competitorIndustryDTO.getId() != null) {
            throw new BadRequestAlertException("A new competitorIndustry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitorIndustryDTO result = competitorIndustryService.save(competitorIndustryDTO);
        return ResponseEntity.created(new URI("/api/competitor-industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitor-industries} : Updates an existing competitorIndustry.
     *
     * @param competitorIndustryDTO the competitorIndustryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitorIndustryDTO,
     * or with status {@code 400 (Bad Request)} if the competitorIndustryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitorIndustryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitor-industries")
    public ResponseEntity<CompetitorIndustryDTO> updateCompetitorIndustry(@Valid @RequestBody CompetitorIndustryDTO competitorIndustryDTO) throws URISyntaxException {
        log.debug("REST request to update CompetitorIndustry : {}", competitorIndustryDTO);
        if (competitorIndustryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitorIndustryDTO result = competitorIndustryService.save(competitorIndustryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitorIndustryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competitor-industries} : get all the competitorIndustries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitorIndustries in body.
     */
    @GetMapping("/competitor-industries")
    public List<CompetitorIndustryDTO> getAllCompetitorIndustries() {
        log.debug("REST request to get all CompetitorIndustries");
        return competitorIndustryService.findAll();
    }

    /**
     * {@code GET  /competitor-industries/:id} : get the "id" competitorIndustry.
     *
     * @param id the id of the competitorIndustryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitorIndustryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitor-industries/{id}")
    public ResponseEntity<CompetitorIndustryDTO> getCompetitorIndustry(@PathVariable Long id) {
        log.debug("REST request to get CompetitorIndustry : {}", id);
        Optional<CompetitorIndustryDTO> competitorIndustryDTO = competitorIndustryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitorIndustryDTO);
    }

    /**
     * {@code DELETE  /competitor-industries/:id} : delete the "id" competitorIndustry.
     *
     * @param id the id of the competitorIndustryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitor-industries/{id}")
    public ResponseEntity<Void> deleteCompetitorIndustry(@PathVariable Long id) {
        log.debug("REST request to delete CompetitorIndustry : {}", id);
        competitorIndustryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /competitor-industries/competitor/{id}} : get all the competitorIndustries by competitor id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitorIndustries in body.
     */
    @GetMapping("/competitor-industries/competitor/{id}")
    public ResponseEntity<List<CompetitorIndustryDTO>> getAllCompetitorIndustries(@PathVariable Long id) {
        log.debug("REST request to get all CompetitorIndustries by competitor id {}", id);
        return ResponseEntity.ok(competitorIndustryService.findAllByCompetitorId(id));
    }

    /**
     * {@code POST  /competitor-industries/competitor} : Create a new competitorIndustry.
     *
     * @param list of the competitorIndustryDTOs to refresh.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the list of new competitorIndustryDTO, or with status {@code 400 (Bad Request)} if the competitorIndustry ID not exist.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitor-industries/competitor")
    public ResponseEntity<List<CompetitorIndustryDTO>> refreshCompetitorIndustry(@RequestBody List<CompetitorIndustryDTO> list) throws URISyntaxException {
        log.debug("REST request to refresh CompetitorIndustries list: {}", list);
        List<CompetitorIndustryDTO> created = new ArrayList<>();
        for (CompetitorIndustryDTO dto : list) {
            if (dto.getId() == null) {
                created.add(this.competitorIndustryService.save(dto));
            } else if (this.competitorIndustryService.findOne(dto.getId()).isPresent()) {
                this.competitorIndustryService.delete(dto.getId());
            } else {
                throw new BadRequestAlertException("Can not delete competitorIndustry, id " + dto.getId() + " does not exist", ENTITY_NAME, "idnotexists");
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, created.toString()))
            .body(created);
    }
}
