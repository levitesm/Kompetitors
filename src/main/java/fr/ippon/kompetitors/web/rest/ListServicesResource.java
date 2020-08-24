package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.ListServicesService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.ListServicesDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListServices}.
 */
@RestController
@RequestMapping("/api")
public class ListServicesResource {

    private final Logger log = LoggerFactory.getLogger(ListServicesResource.class);

    private static final String ENTITY_NAME = "listServices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListServicesService listServicesService;

    public ListServicesResource(ListServicesService listServicesService) {
        this.listServicesService = listServicesService;
    }

    /**
     * {@code POST  /list-services} : Create a new listServices.
     *
     * @param listServicesDTO the listServicesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listServicesDTO, or with status {@code 400 (Bad Request)} if the listServices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-services")
    public ResponseEntity<ListServicesDTO> createListServices(@Valid @RequestBody ListServicesDTO listServicesDTO) throws URISyntaxException {
        log.debug("REST request to save ListServices : {}", listServicesDTO);
        if (listServicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new listServices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListServicesDTO result = listServicesService.save(listServicesDTO);
        return ResponseEntity.created(new URI("/api/list-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-services} : Updates an existing listServices.
     *
     * @param listServicesDTO the listServicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listServicesDTO,
     * or with status {@code 400 (Bad Request)} if the listServicesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listServicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-services")
    public ResponseEntity<ListServicesDTO> updateListServices(@Valid @RequestBody ListServicesDTO listServicesDTO) throws URISyntaxException {
        log.debug("REST request to update ListServices : {}", listServicesDTO);
        if (listServicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListServicesDTO result = listServicesService.save(listServicesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listServicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-services} : get all the listServices.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listServices in body.
     */
    @GetMapping("/list-services")
    public List<ListServicesDTO> getAllListServices() {
        log.debug("REST request to get all ListServices");
        return listServicesService.findAll();
    }

    /**
     * {@code GET  /list-services/:id} : get the "id" listServices.
     *
     * @param id the id of the listServicesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listServicesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-services/{id}")
    public ResponseEntity<ListServicesDTO> getListServices(@PathVariable Long id) {
        log.debug("REST request to get ListServices : {}", id);
        Optional<ListServicesDTO> listServicesDTO = listServicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listServicesDTO);
    }

    /**
     * {@code DELETE  /list-services/:id} : delete the "id" listServices.
     *
     * @param id the id of the listServicesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-services/{id}")
    public ResponseEntity<Void> deleteListServices(@PathVariable Long id) {
        log.debug("REST request to delete ListServices : {}", id);
        listServicesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
