package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.ExternalUrlsService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.ExternalUrlsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ExternalUrls}.
 */
@RestController
@RequestMapping("/api")
public class ExternalUrlsResource {

    private final Logger log = LoggerFactory.getLogger(ExternalUrlsResource.class);

    private static final String ENTITY_NAME = "externalUrls";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExternalUrlsService externalUrlsService;

    public ExternalUrlsResource(ExternalUrlsService externalUrlsService) {
        this.externalUrlsService = externalUrlsService;
    }

    /**
     * {@code POST  /external-urls} : Create a new externalUrls.
     *
     * @param externalUrlsDTO the externalUrlsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new externalUrlsDTO, or with status {@code 400 (Bad Request)} if the externalUrls has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/external-urls")
    public ResponseEntity<ExternalUrlsDTO> createExternalUrls(@RequestBody ExternalUrlsDTO externalUrlsDTO) throws URISyntaxException {
        log.debug("REST request to save ExternalUrls : {}", externalUrlsDTO);
        if (externalUrlsDTO.getId() != null) {
            throw new BadRequestAlertException("A new externalUrls cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExternalUrlsDTO result = externalUrlsService.save(externalUrlsDTO);
        return ResponseEntity.created(new URI("/api/external-urls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /external-urls} : Updates an existing externalUrls.
     *
     * @param externalUrlsDTO the externalUrlsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated externalUrlsDTO,
     * or with status {@code 400 (Bad Request)} if the externalUrlsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the externalUrlsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/external-urls")
    public ResponseEntity<ExternalUrlsDTO> updateExternalUrls(@RequestBody ExternalUrlsDTO externalUrlsDTO) throws URISyntaxException {
        log.debug("REST request to update ExternalUrls : {}", externalUrlsDTO);
        if (externalUrlsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExternalUrlsDTO result = externalUrlsService.save(externalUrlsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, externalUrlsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /external-urls} : get all the externalUrls.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of externalUrls in body.
     */
    @GetMapping("/external-urls")
    public ResponseEntity<List<ExternalUrlsDTO>> getAllExternalUrls(Pageable pageable) {
        log.debug("REST request to get a page of ExternalUrls");
        Page<ExternalUrlsDTO> page = externalUrlsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /external-urls/:id} : get the "id" externalUrls.
     *
     * @param id the id of the externalUrlsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the externalUrlsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/external-urls/{id}")
    public ResponseEntity<ExternalUrlsDTO> getExternalUrls(@PathVariable Long id) {
        log.debug("REST request to get ExternalUrls : {}", id);
        Optional<ExternalUrlsDTO> externalUrlsDTO = externalUrlsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(externalUrlsDTO);
    }

    /**
     * {@code DELETE  /external-urls/:id} : delete the "id" externalUrls.
     *
     * @param id the id of the externalUrlsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/external-urls/{id}")
    public ResponseEntity<Void> deleteExternalUrls(@PathVariable Long id) {
        log.debug("REST request to delete ExternalUrls : {}", id);
        externalUrlsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /external-urls/competitor/:id} : get the "id" externalUrls by competitor id.
     *
     * @param id the id of the competitor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the externalUrlsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/external-urls/competitor/{id}")
    public ResponseEntity<ExternalUrlsDTO> getExternalUrlsByCompetitorId(@PathVariable Long id) {
        log.debug("REST request to get ExternalUrls by competitor id : {}", id);
        Optional<ExternalUrlsDTO> externalUrlsDTO = externalUrlsService.findByCompetitorId(id);
        return ResponseUtil.wrapOrNotFound(externalUrlsDTO);
    }
}
