package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.HrInfo;
import fr.ippon.kompetitors.repository.HrInfoRepository;
import fr.ippon.kompetitors.service.dto.HRDashboardDTO;
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

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.HrInfo}.
 */
@RestController
@RequestMapping("/api")
public class HrInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrInfoResource.class);

    private static final String ENTITY_NAME = "hrInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HrInfoRepository hrInfoRepository;

    public HrInfoResource(HrInfoRepository hrInfoRepository) {
        this.hrInfoRepository = hrInfoRepository;
    }

    /**
     * {@code POST  /hr-infos} : Create a new hrInfo.
     *
     * @param hrInfo the hrInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hrInfo, or with status {@code 400 (Bad Request)} if the hrInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hr-infos")
    public ResponseEntity<HrInfo> createHrInfo(@RequestBody HrInfo hrInfo) throws URISyntaxException {
        log.debug("REST request to save HrInfo : {}", hrInfo);
        if (hrInfo.getId() != null) {
            throw new BadRequestAlertException("A new hrInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HrInfo result = hrInfoRepository.save(hrInfo);
        return ResponseEntity.created(new URI("/api/hr-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hr-infos} : Updates an existing hrInfo.
     *
     * @param hrInfo the hrInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hrInfo,
     * or with status {@code 400 (Bad Request)} if the hrInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hrInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hr-infos")
    public ResponseEntity<HrInfo> updateHrInfo(@RequestBody HrInfo hrInfo) throws URISyntaxException {
        log.debug("REST request to update HrInfo : {}", hrInfo);
        if (hrInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HrInfo result = hrInfoRepository.save(hrInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hrInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hr-infos} : get all the hrInfos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hrInfos in body.
     */
    @GetMapping("/hr-infos")
    public List<HrInfo> getAllHrInfos() {
        log.debug("REST request to get all HrInfos");
        return hrInfoRepository.findAll();
    }

    /**
     * {@code GET  /hr-infos/:id} : get the "id" hrInfo.
     *
     * @param id the id of the hrInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hrInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hr-infos/{id}")
    public ResponseEntity<HrInfo> getHrInfo(@PathVariable Long id) {
        log.debug("REST request to get HrInfo : {}", id);
        Optional<HrInfo> hrInfo = hrInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hrInfo);
    }

    /**
     * {@code DELETE  /hr-infos/:id} : delete the "id" hrInfo.
     *
     * @param id the id of the hrInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hr-infos/{id}")
    public ResponseEntity<Void> deleteHrInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrInfo : {}", id);
        hrInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /hr-infos/:id} : get the "id" hrInfo.
     *
     * @param id the id of the hrInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hrInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hr-infos/dashboard-hr/competitor/{id}")
    public ResponseEntity<HRDashboardDTO> getHRDashboardByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get Hr dashboard for competitor : {}", id);
        Optional<HRDashboardDTO> hrDashboard = hrInfoRepository.getHRDashboardByCompetitorId(id);
        return ResponseUtil.wrapOrNotFound(hrDashboard);
    }

    /**
     * {@code GET  /hr-infos/:id} : get the "id" hrInfo.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hrInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hr-infos/dashboard-hr")
    public List<HRDashboardDTO> getHRDashboardAll() {
        log.debug("REST request to get Hr dashboard ALL");

        return hrInfoRepository.getHRDashboardAll();
    }
}
