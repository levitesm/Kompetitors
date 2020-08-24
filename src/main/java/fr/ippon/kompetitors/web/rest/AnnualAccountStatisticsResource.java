package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.AnnualAccountStatisticsFetchService;
import fr.ippon.kompetitors.service.AnnualAccountStatisticsService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.AnnualAccountStatisticsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.AnnualAccountStatistics}.
 */
@RestController
@RequestMapping("/api")
public class AnnualAccountStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(AnnualAccountStatisticsResource.class);

    private static final String ENTITY_NAME = "annualAccountStatistics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnnualAccountStatisticsService annualAccountStatisticsService;
    private final AnnualAccountStatisticsFetchService annualAccountStatisticsFetchService;

    public AnnualAccountStatisticsResource(AnnualAccountStatisticsService annualAccountStatisticsService,
                                           AnnualAccountStatisticsFetchService annualAccountStatisticsFetchService) {
        this.annualAccountStatisticsService = annualAccountStatisticsService;
        this.annualAccountStatisticsFetchService = annualAccountStatisticsFetchService;
    }

    /**
     * {@code POST  /annual-account-statistics} : Create a new annualAccountStatistics.
     *
     * @param annualAccountStatisticsDTO the annualAccountStatisticsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annualAccountStatisticsDTO, or with status {@code 400 (Bad Request)} if the annualAccountStatistics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/annual-account-statistics")
    public ResponseEntity<AnnualAccountStatisticsDTO> createAnnualAccountStatistics(@Valid @RequestBody AnnualAccountStatisticsDTO annualAccountStatisticsDTO) throws URISyntaxException {
        log.debug("REST request to save AnnualAccountStatistics : {}", annualAccountStatisticsDTO);
        if (annualAccountStatisticsDTO.getId() != null) {
            throw new BadRequestAlertException("A new annualAccountStatistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnnualAccountStatisticsDTO result = annualAccountStatisticsService.save(annualAccountStatisticsDTO);
        return ResponseEntity.created(new URI("/api/annual-account-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /annual-account-statistics} : Updates an existing annualAccountStatistics.
     *
     * @param annualAccountStatisticsDTO the annualAccountStatisticsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annualAccountStatisticsDTO,
     * or with status {@code 400 (Bad Request)} if the annualAccountStatisticsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the annualAccountStatisticsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/annual-account-statistics")
    public ResponseEntity<AnnualAccountStatisticsDTO> updateAnnualAccountStatistics(@Valid @RequestBody AnnualAccountStatisticsDTO annualAccountStatisticsDTO) throws URISyntaxException {
        log.debug("REST request to update AnnualAccountStatistics : {}", annualAccountStatisticsDTO);
        if (annualAccountStatisticsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnnualAccountStatisticsDTO result = annualAccountStatisticsService.save(annualAccountStatisticsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annualAccountStatisticsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /annual-account-statistics} : get all the annualAccountStatistics.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annualAccountStatistics in body.
     */
    @GetMapping("/annual-account-statistics")
    public ResponseEntity<List<AnnualAccountStatisticsDTO>> getAllAnnualAccountStatistics(Pageable pageable) {
        log.debug("REST request to get a page of AnnualAccountStatistics");
        Page<AnnualAccountStatisticsDTO> page = annualAccountStatisticsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /annual-account-statistics/:id} : get the "id" annualAccountStatistics.
     *
     * @param id the id of the annualAccountStatisticsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annualAccountStatisticsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/annual-account-statistics/{id}")
    public ResponseEntity<AnnualAccountStatisticsDTO> getAnnualAccountStatistics(@PathVariable Long id) {
        log.debug("REST request to get AnnualAccountStatistics : {}", id);
        Optional<AnnualAccountStatisticsDTO> annualAccountStatisticsDTO = annualAccountStatisticsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(annualAccountStatisticsDTO);
    }

    /**
     * {@code DELETE  /annual-account-statistics/:id} : delete the "id" annualAccountStatistics.
     *
     * @param id the id of the annualAccountStatisticsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/annual-account-statistics/{id}")
    public ResponseEntity<Void> deleteAnnualAccountStatistics(@PathVariable Long id) {
        log.debug("REST request to delete AnnualAccountStatistics : {}", id);
        annualAccountStatisticsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /annual-account-statistics/fetch} : update all the annualAccountStatistics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/annual-account-statistics/fetch")
    public ResponseEntity updateAllAnnualAccountStatistics() {
        log.debug("REST request to fetch all AnnualAccountStatistics");
        annualAccountStatisticsFetchService.fetchAnnualAccountStatistics();
        return ResponseEntity.ok("Update process started.");
    }

    /**
     * {@code GET  /annual-account-statistics/fetch/{siren}} : update all the annualAccountStatistics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/annual-account-statistics/fetch/{siren}")
    public ResponseEntity updateAllAnnualAccountStatistics(@PathVariable String siren) {
        log.debug("REST request to fetch AnnualAccountStatistics for siren {}", siren);
        annualAccountStatisticsFetchService.fetchAnnualAccountStatisticsBySiren(siren);
        return ResponseEntity.ok("Update process started.");
    }
}
