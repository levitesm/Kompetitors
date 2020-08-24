package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.PrInfoService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.PrInfoDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.PrInfo}.
 */
@RestController
@RequestMapping("/api")
public class PrInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrInfoResource.class);

    private static final String ENTITY_NAME = "prInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrInfoService prInfoService;

    public PrInfoResource(PrInfoService prInfoService) {
        this.prInfoService = prInfoService;
    }

    /**
     * {@code POST  /pr-infos} : Create a new prInfo.
     *
     * @param prInfoDTO the prInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prInfoDTO, or with status {@code 400 (Bad Request)} if the prInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pr-infos")
    public ResponseEntity<PrInfoDTO> createPrInfo(@RequestBody PrInfoDTO prInfoDTO) throws URISyntaxException {
        log.debug("REST request to save PrInfo : {}", prInfoDTO);
        if (prInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new prInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrInfoDTO result = prInfoService.save(prInfoDTO);
        return ResponseEntity.created(new URI("/api/pr-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pr-infos} : Updates an existing prInfo.
     *
     * @param prInfoDTO the prInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prInfoDTO,
     * or with status {@code 400 (Bad Request)} if the prInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pr-infos")
    public ResponseEntity<PrInfoDTO> updatePrInfo(@RequestBody PrInfoDTO prInfoDTO) throws URISyntaxException {
        log.debug("REST request to update PrInfo : {}", prInfoDTO);
        if (prInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrInfoDTO result = prInfoService.save(prInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pr-infos} : get all the prInfos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prInfos in body.
     */
    @GetMapping("/pr-infos")
    public List<PrInfoDTO> getAllPrInfos() {
        log.debug("REST request to get all PrInfos");
        return prInfoService.findAll();
    }

    /**
     * {@code GET  /pr-infos/:id} : get the "id" prInfo.
     *
     * @param id the id of the prInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pr-infos/{id}")
    public ResponseEntity<PrInfoDTO> getPrInfo(@PathVariable Long id) {
        log.debug("REST request to get PrInfo : {}", id);
        Optional<PrInfoDTO> prInfoDTO = prInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prInfoDTO);
    }

    /**
     * {@code DELETE  /pr-infos/:id} : delete the "id" prInfo.
     *
     * @param id the id of the prInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pr-infos/{id}")
    public ResponseEntity<Void> deletePrInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrInfo : {}", id);
        prInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /pr-infos/competitor/:id} : get last prInfo by competitor id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and prInfos in body.
     */
    @GetMapping("/pr-infos/competitor/{id}")
    public ResponseEntity<PrInfoDTO> getLastPrInfoByCompetitorId(@PathVariable Long id) {
        System.out.println("!!!!!!!!!!!! " + id);
        log.debug("REST request to get last prInfo by competitor id");
        Optional<PrInfoDTO> result = prInfoService.findPrInfoByCompetitorId(id);
        return ResponseUtil.wrapOrNotFound(result);
    }
}
