package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ShareHolders;
import fr.ippon.kompetitors.repository.ShareHoldersRepository;
import fr.ippon.kompetitors.service.InfogreffeService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ShareHolders}.
 */
@RestController
@RequestMapping("/api")
public class ShareHoldersResource {

    private final Logger log = LoggerFactory.getLogger(ShareHoldersResource.class);

    private static final String ENTITY_NAME = "shareHolders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShareHoldersRepository shareHoldersRepository;

    private final InfogreffeService infogreffeService;

    public ShareHoldersResource(ShareHoldersRepository shareHoldersRepository, InfogreffeService infogreffeService) {
        this.shareHoldersRepository = shareHoldersRepository;
        this.infogreffeService = infogreffeService;
    }

    /**
     * {@code POST  /share-holders} : Create a new shareHolders.
     *
     * @param shareHolders the shareHolders to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shareHolders, or with status {@code 400 (Bad Request)} if the shareHolders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/share-holders")
    public ResponseEntity<ShareHolders> createShareHolders(@RequestBody ShareHolders shareHolders) throws URISyntaxException {
        log.debug("REST request to save ShareHolders : {}", shareHolders);
        if (shareHolders.getId() != null) {
            throw new BadRequestAlertException("A new shareHolders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShareHolders result = shareHoldersRepository.save(shareHolders);
        return ResponseEntity.created(new URI("/api/share-holders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /share-holders} : Updates an existing shareHolders.
     *
     * @param shareHolders the shareHolders to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shareHolders,
     * or with status {@code 400 (Bad Request)} if the shareHolders is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shareHolders couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/share-holders")
    public ResponseEntity<ShareHolders> updateShareHolders(@RequestBody ShareHolders shareHolders) throws URISyntaxException {
        log.debug("REST request to update ShareHolders : {}", shareHolders);
        if (shareHolders.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShareHolders result = shareHoldersRepository.save(shareHolders);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shareHolders.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /share-holders} : get all the shareHolders.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shareHolders in body.
     */
    @GetMapping("/share-holders")
    public List<ShareHolders> getAllShareHolders() {
        log.debug("REST request to get all ShareHolders");
        return shareHoldersRepository.findAll();
    }

    /**
     * {@code GET  /share-holders/:id} : get the "id" shareHolders.
     *
     * @param id the id of the shareHolders to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shareHolders, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/share-holders/{id}")
    public ResponseEntity<ShareHolders> getShareHolders(@PathVariable Long id) {
        log.debug("REST request to get ShareHolders : {}", id);
        Optional<ShareHolders> shareHolders = shareHoldersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shareHolders);
    }

    /**
     * {@code DELETE  /share-holders/:id} : delete the "id" shareHolders.
     *
     * @param id the id of the shareHolders to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/share-holders/{id}")
    public ResponseEntity<Void> deleteShareHolders(@PathVariable Long id) {
        log.debug("REST request to delete ShareHolders : {}", id);
        shareHoldersRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/share-holders/siren/{siren}")
    public List<ShareHolders> getShareHoldersBySiren(@PathVariable String siren) {
        log.debug("REST request to get ShareHolders by SIREN : {}", siren);
        List<String> sirenList = new ArrayList<>();
        sirenList.add(siren);
        return shareHoldersRepository.findAllByCompetitorSiren(sirenList);
    }

    @PutMapping("/share-holders/siren/{siren}")
    public void updateShareHoldersBySiren(@PathVariable String siren) {
        log.debug("REST request to UPDATE ShareHolders by SIREN : {}", siren);
        infogreffeService.updateShareHolders(siren);
    }
}
