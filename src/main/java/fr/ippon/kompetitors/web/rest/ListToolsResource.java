package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.ListToolsService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.ListToolsDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListTools}.
 */
@RestController
@RequestMapping("/api")
public class ListToolsResource {

    private final Logger log = LoggerFactory.getLogger(ListToolsResource.class);

    private static final String ENTITY_NAME = "listTools";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListToolsService listToolsService;

    public ListToolsResource(ListToolsService listToolsService) {
        this.listToolsService = listToolsService;
    }

    /**
     * {@code POST  /list-tools} : Create a new listTools.
     *
     * @param listToolsDTO the listToolsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listToolsDTO, or with status {@code 400 (Bad Request)} if the listTools has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-tools")
    public ResponseEntity<ListToolsDTO> createListTools(@Valid @RequestBody ListToolsDTO listToolsDTO) throws URISyntaxException {
        log.debug("REST request to save ListTools : {}", listToolsDTO);
        if (listToolsDTO.getId() != null) {
            throw new BadRequestAlertException("A new listTools cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListToolsDTO result = listToolsService.save(listToolsDTO);
        return ResponseEntity.created(new URI("/api/list-tools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-tools} : Updates an existing listTools.
     *
     * @param listToolsDTO the listToolsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listToolsDTO,
     * or with status {@code 400 (Bad Request)} if the listToolsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listToolsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-tools")
    public ResponseEntity<ListToolsDTO> updateListTools(@Valid @RequestBody ListToolsDTO listToolsDTO) throws URISyntaxException {
        log.debug("REST request to update ListTools : {}", listToolsDTO);
        if (listToolsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListToolsDTO result = listToolsService.save(listToolsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listToolsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-tools} : get all the listTools.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listTools in body.
     */
    @GetMapping("/list-tools")
    public List<ListToolsDTO> getAllListTools() {
        log.debug("REST request to get all ListTools");
        return listToolsService.findAll();
    }

    /**
     * {@code GET  /list-tools/:id} : get the "id" listTools.
     *
     * @param id the id of the listToolsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listToolsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-tools/{id}")
    public ResponseEntity<ListToolsDTO> getListTools(@PathVariable Long id) {
        log.debug("REST request to get ListTools : {}", id);
        Optional<ListToolsDTO> listToolsDTO = listToolsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listToolsDTO);
    }

    /**
     * {@code DELETE  /list-tools/:id} : delete the "id" listTools.
     *
     * @param id the id of the listToolsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-tools/{id}")
    public ResponseEntity<Void> deleteListTools(@PathVariable Long id) {
        log.debug("REST request to delete ListTools : {}", id);
        listToolsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
