package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.ListPracticesService;
import fr.ippon.kompetitors.service.dto.ListPracticesDTO;
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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListPractices}.
 */
@RestController
@RequestMapping("/api")
public class ListPracticesResource {

    private final Logger log = LoggerFactory.getLogger(ListPracticesResource.class);

    private static final String ENTITY_NAME = "listPractices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListPracticesService listPracticesService;

    public ListPracticesResource(ListPracticesService listPracticesService) {
        this.listPracticesService = listPracticesService;
    }

    /**
     * {@code POST  /list-practices} : Create a new listPractices.
     *
     * @param listPracticesDTO the listPracticesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listPracticesDTO, or with status {@code 400 (Bad Request)} if the listPractices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-practices")
    public ResponseEntity<ListPracticesDTO> createListPractices(@Valid @RequestBody ListPracticesDTO listPracticesDTO) throws URISyntaxException {
        log.debug("REST request to save ListPractices : {}", listPracticesDTO);
        if (listPracticesDTO.getId() != null) {
            throw new BadRequestAlertException("A new listPractices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListPracticesDTO result = listPracticesService.save(listPracticesDTO);
        return ResponseEntity.created(new URI("/api/list-practices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-practices} : Updates an existing listPractices.
     *
     * @param listPracticesDTO the listPracticesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listPracticesDTO,
     * or with status {@code 400 (Bad Request)} if the listPracticesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listPracticesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-practices")
    public ResponseEntity<ListPracticesDTO> updateListPractices(@Valid @RequestBody ListPracticesDTO listPracticesDTO) throws URISyntaxException {
        log.debug("REST request to update ListPractices : {}", listPracticesDTO);
        if (listPracticesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListPracticesDTO result = listPracticesService.save(listPracticesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listPracticesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-practices} : get all the listPractices.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listPractices in body.
     */
    @GetMapping("/list-practices")
    public List<ListPracticesDTO> getAllListPractices() {
        log.debug("REST request to get all ListPractices");
        return listPracticesService.findAll();
    }

    /**
     * {@code GET  /list-practices/:id} : get the "id" listPractices.
     *
     * @param id the id of the listPracticesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listPracticesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-practices/{id}")
    public ResponseEntity<ListPracticesDTO> getListPractices(@PathVariable Long id) {
        log.debug("REST request to get ListPractices : {}", id);
        Optional<ListPracticesDTO> listPracticesDTO = listPracticesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listPracticesDTO);
    }

    /**
     * {@code DELETE  /list-practices/:id} : delete the "id" listPractices.
     *
     * @param id the id of the listPracticesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-practices/{id}")
    public ResponseEntity<Void> deleteListPractices(@PathVariable Long id) {
        log.debug("REST request to delete ListPractices : {}", id);
        listPracticesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
