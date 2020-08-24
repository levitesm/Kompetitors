package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.DashboardFinancePrepareService;
import fr.ippon.kompetitors.service.DashboardFinanceService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.DashboardFinanceDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.DashboardFinance}.
 */
@RestController
@RequestMapping("/api")
public class DashboardFinanceResource {

    private final Logger log = LoggerFactory.getLogger(DashboardFinanceResource.class);

    private static final String ENTITY_NAME = "dashboardFinance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DashboardFinanceService dashboardFinanceService;
    private final DashboardFinancePrepareService dashboardFinancePrepareService;

    public DashboardFinanceResource(DashboardFinanceService dashboardFinanceService, DashboardFinancePrepareService dashboardFinancePrepareService) {
        this.dashboardFinanceService = dashboardFinanceService;
        this.dashboardFinancePrepareService = dashboardFinancePrepareService;
    }

    /**
     * {@code POST  /dashboard-finances} : Create a new dashboardFinance.
     *
     * @param dashboardFinanceDTO the dashboardFinanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dashboardFinanceDTO, or with status {@code 400 (Bad Request)} if the dashboardFinance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dashboard-finances")
    public ResponseEntity<DashboardFinanceDTO> createDashboardFinance(@RequestBody DashboardFinanceDTO dashboardFinanceDTO) throws URISyntaxException {
        log.debug("REST request to save DashboardFinance : {}", dashboardFinanceDTO);
        if (dashboardFinanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new dashboardFinance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DashboardFinanceDTO result = dashboardFinanceService.save(dashboardFinanceDTO);
        return ResponseEntity.created(new URI("/api/dashboard-finances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dashboard-finances} : Updates an existing dashboardFinance.
     *
     * @param dashboardFinanceDTO the dashboardFinanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dashboardFinanceDTO,
     * or with status {@code 400 (Bad Request)} if the dashboardFinanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dashboardFinanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dashboard-finances")
    public ResponseEntity<DashboardFinanceDTO> updateDashboardFinance(@RequestBody DashboardFinanceDTO dashboardFinanceDTO) throws URISyntaxException {
        log.debug("REST request to update DashboardFinance : {}", dashboardFinanceDTO);
        if (dashboardFinanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DashboardFinanceDTO result = dashboardFinanceService.save(dashboardFinanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dashboardFinanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dashboard-finances} : get all the dashboardFinances.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dashboardFinances in body.
     */
    @GetMapping("/dashboard-finances")
    public List<DashboardFinanceDTO> getAllDashboardFinances() {
        log.debug("REST request to get all DashboardFinances");
        return dashboardFinanceService.findAll();
    }

    /**
     * {@code GET  /dashboard-finances/:id} : get the "id" dashboardFinance.
     *
     * @param id the id of the dashboardFinanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dashboardFinanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dashboard-finances/{id}")
    public ResponseEntity<DashboardFinanceDTO> getDashboardFinance(@PathVariable Long id) {
        log.debug("REST request to get DashboardFinance : {}", id);
        Optional<DashboardFinanceDTO> dashboardFinanceDTO = dashboardFinanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dashboardFinanceDTO);
    }

    /**
     * {@code DELETE  /dashboard-finances/:id} : delete the "id" dashboardFinance.
     *
     * @param id the id of the dashboardFinanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dashboard-finances/{id}")
    public ResponseEntity<Void> deleteDashboardFinance(@PathVariable Long id) {
        log.debug("REST request to delete DashboardFinance : {}", id);
        dashboardFinanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /dashboard-finances/year/:year} : get all the dashboardFinances for year.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dashboardFinances in body.
     */
    @GetMapping("/dashboard-finances/year/{year}")
    public List<DashboardFinanceDTO> getAllDashboardFinancesForYear(@PathVariable Integer year) {
        log.debug("REST request to get all DashboardFinances for year {}", year);
        return dashboardFinanceService.findAllForYear(year);
    }

    /**
     * {@code GET  /dashboard-finances/year/:year/update} : update all the dashboardFinances for year.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} updated list of dashboardFinances in body.
     */
    @GetMapping("/dashboard-finances/year/{year}/update")
    public List<DashboardFinanceDTO> updateAllDashboardFinancesForYear(@PathVariable Integer year) {
        log.debug("REST request to update all DashboardFinances for year {}", year);
        return dashboardFinancePrepareService.prepareDashboardForYear(year, true);
    }

    /**
     * {@code GET  /dashboard-finances/competitor/:id} : get all the dashboardFinances for last available year by competitor id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dashboardFinances in body.
     */
    @GetMapping("/dashboard-finances/competitor/{id}")
    public List<DashboardFinanceDTO> getAllDashboardFinancesForYear(@PathVariable Long id) {
        log.debug("REST request to get all DashboardFinances for last available year by competitor id {}", id);
        return dashboardFinanceService.findAllLastAvailableYear(id);
    }
}
