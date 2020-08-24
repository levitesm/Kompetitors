package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.UserGroupRights;
import fr.ippon.kompetitors.repository.UserGroupRightsRepository;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.UserGroupRights}.
 */
@RestController
@RequestMapping("/api")
public class UserGroupRightsResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupRightsResource.class);

    private static final String ENTITY_NAME = "userGroupRights";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGroupRightsRepository userGroupRightsRepository;

    public UserGroupRightsResource(UserGroupRightsRepository userGroupRightsRepository) {
        this.userGroupRightsRepository = userGroupRightsRepository;
    }

    /**
     * {@code POST  /user-group-rights} : Create a new userGroupRights.
     *
     * @param userGroupRights the userGroupRights to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGroupRights, or with status {@code 400 (Bad Request)} if the userGroupRights has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-group-rights")
    public ResponseEntity<UserGroupRights> createUserGroupRights(@Valid @RequestBody UserGroupRights userGroupRights) throws URISyntaxException {
        log.debug("REST request to save UserGroupRights : {}", userGroupRights);
        if (userGroupRights.getId() != null) {
            throw new BadRequestAlertException("A new userGroupRights cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGroupRights result = userGroupRightsRepository.save(userGroupRights);
        return ResponseEntity.created(new URI("/api/user-group-rights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-group-rights} : Updates an existing userGroupRights.
     *
     * @param userGroupRights the userGroupRights to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupRights,
     * or with status {@code 400 (Bad Request)} if the userGroupRights is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGroupRights couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-group-rights")
    public ResponseEntity<UserGroupRights> updateUserGroupRights(@Valid @RequestBody UserGroupRights userGroupRights) throws URISyntaxException {
        log.debug("REST request to update UserGroupRights : {}", userGroupRights);
        if (userGroupRights.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserGroupRights result = userGroupRightsRepository.save(userGroupRights);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userGroupRights.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-group-rights} : get all the userGroupRights.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGroupRights in body.
     */
    @GetMapping("/user-group-rights")
    public List<UserGroupRights> getAllUserGroupRights() {
        log.debug("REST request to get all UserGroupRights");
        return userGroupRightsRepository.findAll();
    }

    /**
     * {@code GET  /user-group-rights/:id} : get the "id" userGroupRights.
     *
     * @param id the id of the userGroupRights to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGroupRights, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-group-rights/{id}")
    public ResponseEntity<UserGroupRights> getUserGroupRights(@PathVariable Long id) {
        log.debug("REST request to get UserGroupRights : {}", id);
        Optional<UserGroupRights> userGroupRights = userGroupRightsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userGroupRights);
    }

    /**
     * {@code DELETE  /user-group-rights/:id} : delete the "id" userGroupRights.
     *
     * @param id the id of the userGroupRights to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-group-rights/{id}")
    public ResponseEntity<Void> deleteUserGroupRights(@PathVariable Long id) {
        log.debug("REST request to delete UserGroupRights : {}", id);
        userGroupRightsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
