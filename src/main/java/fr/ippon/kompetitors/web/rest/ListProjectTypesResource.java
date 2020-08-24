package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.ListProjectTypesService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.ListProjectTypesDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListProjectTypes}.
 */
@RestController
@RequestMapping("/api")
public class ListProjectTypesResource {

    private final Logger log = LoggerFactory.getLogger(ListProjectTypesResource.class);

    private static final String ENTITY_NAME = "listProjectTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListProjectTypesService listProjectTypesService;

    public ListProjectTypesResource(ListProjectTypesService listProjectTypesService) {
        this.listProjectTypesService = listProjectTypesService;
    }

    /**
     * {@code POST  /list-project-types} : Create a new listProjectTypes.
     *
     * @param listProjectTypesDTO the listProjectTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listProjectTypesDTO, or with status {@code 400 (Bad Request)} if the listProjectTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-project-types")
    public ResponseEntity<ListProjectTypesDTO> createListProjectTypes(@Valid @RequestBody ListProjectTypesDTO listProjectTypesDTO) throws URISyntaxException {
        log.debug("REST request to save ListProjectTypes : {}", listProjectTypesDTO);
        if (listProjectTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new listProjectTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListProjectTypesDTO result = listProjectTypesService.save(listProjectTypesDTO);
        return ResponseEntity.created(new URI("/api/list-project-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-project-types} : Updates an existing listProjectTypes.
     *
     * @param listProjectTypesDTO the listProjectTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listProjectTypesDTO,
     * or with status {@code 400 (Bad Request)} if the listProjectTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listProjectTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-project-types")
    public ResponseEntity<ListProjectTypesDTO> updateListProjectTypes(@Valid @RequestBody ListProjectTypesDTO listProjectTypesDTO) throws URISyntaxException {
        log.debug("REST request to update ListProjectTypes : {}", listProjectTypesDTO);
        if (listProjectTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListProjectTypesDTO result = listProjectTypesService.save(listProjectTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listProjectTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-project-types} : get all the listProjectTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listProjectTypes in body.
     */
    @GetMapping("/list-project-types")
    public List<ListProjectTypesDTO> getAllListProjectTypes() {
        log.debug("REST request to get all ListProjectTypes");
        return listProjectTypesService.findAll();
    }

    /**
     * {@code GET  /list-project-types/:id} : get the "id" listProjectTypes.
     *
     * @param id the id of the listProjectTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listProjectTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-project-types/{id}")
    public ResponseEntity<ListProjectTypesDTO> getListProjectTypes(@PathVariable Long id) {
        log.debug("REST request to get ListProjectTypes : {}", id);
        Optional<ListProjectTypesDTO> listProjectTypesDTO = listProjectTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listProjectTypesDTO);
    }

    /**
     * {@code DELETE  /list-project-types/:id} : delete the "id" listProjectTypes.
     *
     * @param id the id of the listProjectTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-project-types/{id}")
    public ResponseEntity<Void> deleteListProjectTypes(@PathVariable Long id) {
        log.debug("REST request to delete ListProjectTypes : {}", id);
        listProjectTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
