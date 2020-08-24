package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.CompetitiveRates;
import fr.ippon.kompetitors.repository.CompetitiveRatesRepository;
import fr.ippon.kompetitors.service.CompetitiveRateService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.CompetitiveRates}.
 */
@RestController
@RequestMapping("/api")
public class CompetitiveRatesResource {

    private final Logger log = LoggerFactory.getLogger(CompetitiveRatesResource.class);

    private static final String ENTITY_NAME = "competitiveRates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitiveRatesRepository competitiveRatesRepository;

    private final CompetitiveRateService competitiveRateService;

    public CompetitiveRatesResource(CompetitiveRatesRepository competitiveRatesRepository, CompetitiveRateService competitiveRateService) {
        this.competitiveRatesRepository = competitiveRatesRepository;
        this.competitiveRateService = competitiveRateService;
    }

    /**
     * {@code PUT  /competitive-rates} : Updates an existing competitiveRates.
     *
     * @param id the competitor's id to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitiveRates,
     * or with status {@code 400 (Bad Request)} if the competitiveRates is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitiveRates couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitive-rates/{id}/{type}/{value}")
    public ResponseEntity<CompetitiveRates> updateCompetitiveRates(@PathVariable Long id, @PathVariable String type, @PathVariable Double value) throws URISyntaxException {
        log.debug("REST request to update CompetitiveRates : {} - {} - {}", id, type, value);

        CompetitiveRates result = competitiveRateService.save(id, type, value);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .body(result);
    }

    @GetMapping("/competitive-rates/maximum")
    public Double getMaximalTotalRate() {
        log.debug("REST request to get MAXIMAL Total Rate");
        return competitiveRateService.getMaximum();
    }

    /**
     * {@code GET  /competitive-rates} : get all the competitiveRates.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitiveRates in body.
     */
    @GetMapping("/competitive-rates")
    public List<CompetitiveRates> getAllCompetitiveRates() {
        log.debug("REST request to get all CompetitiveRates");
        return competitiveRatesRepository.findAll();
    }

    /**
     * {@code GET  /competitive-rates/:id} : get the "id" competitiveRates.
     *
     * @param id the id of the competitiveRates to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitiveRates, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitive-rates/{id}")
    public ResponseEntity<CompetitiveRates> getCompetitiveRates(@PathVariable Long id) {
        log.debug("REST request to get CompetitiveRates : {}", id);
        Optional<CompetitiveRates> competitiveRates = competitiveRatesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competitiveRates);
    }

    @GetMapping("/competitive-rates/updateall")
    public String updateAllRates() {
        log.debug("REST request to UPDATE ALL RATES");
        try {
            competitiveRateService.updateAll();
        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return e.toString() + " >>> " +e.getMessage() + "  ||  " + sw.toString();
        }
        return "SUCCESS!";
    }

    @GetMapping("/competitive-rates/updateWeights/{f}/{c}/{t}/{h}")
    public String updateRatesWeights(@PathVariable Double f, @PathVariable Double c, @PathVariable Double t, @PathVariable Double h) {
        log.debug("REST request to UPDATE RATES WEIGHTS - Finance = " + f + ", Clients = " + c + ", Technical = " + t + ", HR = " + h);
        try {
            competitiveRateService.updateWeights(f, c, t, h);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return e.toString() + " >>> " +e.getMessage() + "  ||  " + sw.toString();
        }
        return "SUCCESS!";
    }
}
