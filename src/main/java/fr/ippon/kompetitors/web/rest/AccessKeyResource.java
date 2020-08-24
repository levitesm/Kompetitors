package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.AccessKey;
import fr.ippon.kompetitors.service.AccessKeyService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;

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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.AccessKey}.
 */
@RestController
@RequestMapping("/api")
public class AccessKeyResource {

    private final Logger log = LoggerFactory.getLogger(AccessKeyResource.class);

    private static final String ENTITY_NAME = "accessKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccessKeyService accessKeyService;

    public AccessKeyResource(AccessKeyService accessKeyService) {
        this.accessKeyService = accessKeyService;
    }

    /**
     * {@code POST  /access-keys} : Create a new accessKey.
     *
     * @param accessKey the accessKey to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accessKey, or with status {@code 400 (Bad Request)} if the accessKey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/access-keys")
    public ResponseEntity<AccessKey> createAccessKey(@Valid @RequestBody AccessKey accessKey) throws URISyntaxException {
        log.debug("REST request to save AccessKey : {}", accessKey);
        if (accessKey.getId() != null) {
            throw new BadRequestAlertException("A new accessKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccessKey result = accessKeyService.save(accessKey);
        return ResponseEntity.created(new URI("/api/access-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /access-keys} : Updates an existing accessKey.
     *
     * @param accessKey the accessKey to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accessKey,
     * or with status {@code 400 (Bad Request)} if the accessKey is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accessKey couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/access-keys")
    public ResponseEntity<AccessKey> updateAccessKey(@Valid @RequestBody AccessKey accessKey) throws URISyntaxException {
        log.debug("REST request to update AccessKey : {}", accessKey);
        if (accessKey.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccessKey result = accessKeyService.save(accessKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accessKey.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /access-keys} : get all the accessKeys.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accessKeys in body.
     */
    @GetMapping("/access-keys")
    public List<AccessKey> getAllAccessKeys() {
        log.debug("REST request to get all AccessKeys");
        return accessKeyService.findAll();
    }

    /**
     * {@code GET  /access-keys/:id} : get the "id" accessKey.
     *
     * @param id the id of the accessKey to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accessKey, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/access-keys/{id}")
    public ResponseEntity<AccessKey> getAccessKey(@PathVariable Long id) {
        log.debug("REST request to get AccessKey : {}", id);
        Optional<AccessKey> accessKey = accessKeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accessKey);
    }

    /**
     * {@code DELETE  /access-keys/:id} : delete the "id" accessKey.
     *
     * @param id the id of the accessKey to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/access-keys/{id}")
    public ResponseEntity<Void> deleteAccessKey(@PathVariable Long id) {
        log.debug("REST request to delete AccessKey : {}", id);
        accessKeyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
