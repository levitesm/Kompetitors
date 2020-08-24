package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.Competitors;
import fr.ippon.kompetitors.repository.CompetitorsRepository;
import fr.ippon.kompetitors.repository.LegalRepository;
import fr.ippon.kompetitors.service.AnnualAccountStatisticsFetchService;
import fr.ippon.kompetitors.service.CompetitorService;
import fr.ippon.kompetitors.service.dto.CompetitorGroupDTO;
import fr.ippon.kompetitors.service.dto.FillFlagsDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.Competitors}.
 */
@RestController
@RequestMapping("/api")
public class CompetitorsResource {

    private final Logger log = LoggerFactory.getLogger(CompetitorsResource.class);

    private static final String ENTITY_NAME = "competitors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitorsRepository competitorsRepository;
    private final CompetitorService competitorService;
    private final LegalRepository legalRepository;
    private final AnnualAccountStatisticsFetchService annualAccountStatisticsFetchService;

    public CompetitorsResource(CompetitorsRepository competitorsRepository, CompetitorService competitorService,
                               LegalRepository legalRepository, AnnualAccountStatisticsFetchService annualAccountStatisticsFetchService) {
        this.competitorsRepository = competitorsRepository;
        this.competitorService = competitorService;
        this.legalRepository = legalRepository;
        this.annualAccountStatisticsFetchService = annualAccountStatisticsFetchService;
    }

    /**
     * {@code POST  /competitors} : Create a new competitors.
     *
     * @param competitors the competitors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitors, or with status {@code 400 (Bad Request)} if the competitors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitors")
    public ResponseEntity<Competitors> createCompetitors(@RequestBody Competitors competitors) throws URISyntaxException {
        log.debug("REST request to save Competitors : {}", competitors);
        if (competitors.getId() != null) {
            throw new BadRequestAlertException("A new competitors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competitors result = competitorsRepository.save(competitors);
        return ResponseEntity.created(new URI("/api/competitors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitors} : Updates an existing competitors.
     *
     * @param competitors the competitors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitors,
     * or with status {@code 400 (Bad Request)} if the competitors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitors")
    public ResponseEntity<Competitors> updateCompetitors(@RequestBody Competitors competitors) throws URISyntaxException {
        log.debug("REST request to update Competitors : {}", competitors);
        if (competitors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Competitors result = competitorsRepository.save(competitors);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitors.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competitors} : get all the competitors.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitors in body.
     */
    @GetMapping("/competitors")
    public List<Competitors> getAllCompetitors(@RequestParam(required = false) String filter) {
        if ("societemain-is-null".equals(filter)) {
            log.debug("REST request to get all Competitorss where societeMain is null");
            return StreamSupport
                .stream(competitorsRepository.findAll().spliterator(), false)
                .filter(competitors -> competitors.getSocieteMain() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Competitors");
        return competitorsRepository.findAll();
    }

    /**
     * {@code GET  /competitors/:id} : get the "id" competitors.
     *
     * @param id the id of the competitors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitors/{id}")
    public ResponseEntity<Competitors> getCompetitors(@PathVariable Long id) {
        log.debug("REST request to get Competitors : {}", id);
        Optional<Competitors> competitors = competitorsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competitors);
    }

    /**
     * {@code DELETE  /competitors/:id} : delete the "id" competitors.
     *
     * @param id the id of the competitors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitors/{id}")
    public ResponseEntity<Void> deleteCompetitors(@PathVariable Long id) {
        log.debug("REST request to delete Competitors : {}", id);
        competitorService.deleteCompetitorById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /competitors/find?what&where} : get the competitors by Name and Country/City.
     *
     * @param what the name of the competitors to retrieve.
     * @param where the country or city of the competitors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitors/find")
    public List<Competitors> findCompetitors(@RequestParam String what, @RequestParam String where) {
        log.debug("REST request to find Competitors : {} - {}", what, where);
        return competitorsRepository.findByWhatAndWhere(what.toLowerCase(), where.toLowerCase());//findByName(what);
    }

    @GetMapping("/competitors/find/region")
    public List<Competitors> findCompetitorsByRegion(@RequestParam String what, @RequestParam String where, @RequestParam String region) {
        log.debug("REST request to find Competitors by region : {} - {} - {}", what, where, region);
        return competitorsRepository.findByWhatAndWhereAndRegion(what.toLowerCase(), where.toLowerCase(), region);//findByName(what);
    }

    @GetMapping("/competitors/groupId/{groupId}")
    public List<Competitors> findCompetitors(@PathVariable Long groupId) {
        log.debug("REST request to find Competitors by GroupId: {}", groupId);

        return competitorsRepository.findByGlobalGroupsId(groupId);
    }

    /**
     * {@code GET  /competitors/reference} : get reference competitor.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitors/reference")
    public ResponseEntity<Competitors> findReferenceCompetitors() {
        log.debug("REST request to find reference Competitors");
        ResponseEntity<Competitors> result = ResponseEntity.notFound().build();
        try {

            result = ResponseEntity.ok(competitorsRepository.findTopByGlobalGroupsReferenceIsTrueOrderById());
        } catch (Exception e) {
            log.error("Failed to fetch reference competitor", e);
        }
        return result;
    }

    /**
     * {@code POST  /competitors/group} : Create a new competitors.
     *
     * @param dto the entities to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitors, or with status {@code 400 (Bad Request)} if the competitors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitors/group")
    public ResponseEntity<Competitors> createCompetitorOrGroup(@RequestBody CompetitorGroupDTO dto) throws URISyntaxException {
        log.debug("REST request to create new Competitor or GlobalGroup : {}", dto);
        if (dto.getCompetitor().getName() != null && competitorsRepository.findByName(dto.getCompetitor().getName()).size() > 0) {
            throw new BadRequestAlertException("A competitor with that name already exists", ENTITY_NAME, "nameexists");
        }
        if (dto.getLegal().getSiren() != null && legalRepository.findBySiren(dto.getLegal().getSiren()).size() > 0) {
            throw new BadRequestAlertException("A competitor with that siren already exists", ENTITY_NAME, "sirenexists");
        }
        Competitors result = competitorService.createCompetitor(dto.getGlobalGroups(), dto.getLegal(), dto.getOffices(), dto.getCompetitor());
        annualAccountStatisticsFetchService.fetchAnnualAccountStatisticsBySiren(dto.getLegal().getSiren());
        return ResponseEntity.created(new URI("/api/competitors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * {@code GET  /competitors/flags/:id/siren/:siren} : get the "id" competitors.
     *
     * @param id the id of the competitors.
     * @param siren the siren of the competitors.
     * @return FillFlagsDTO
     */
    @GetMapping("/competitors/flags/{id}/siren/{siren}")
    public FillFlagsDTO getCompetitors(@PathVariable Long id, @PathVariable String siren) {
        log.debug("REST request to get Competitors FillFlags : {} - {}", id, siren);
        return competitorsRepository.getFillFlags(id, siren);
    }
}
