package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.ListCompetanciesService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.ListCompetanciesDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListCompetancies}.
 */
@RestController
@RequestMapping("/api")
public class ListCompetanciesResource {

    private final Logger log = LoggerFactory.getLogger(ListCompetanciesResource.class);

    private static final String ENTITY_NAME = "listCompetancies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListCompetanciesService listCompetanciesService;

    public ListCompetanciesResource(ListCompetanciesService listCompetanciesService) {
        this.listCompetanciesService = listCompetanciesService;
    }

    /**
     * {@code POST  /list-competancies} : Create a new listCompetancies.
     *
     * @param listCompetanciesDTO the listCompetanciesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listCompetanciesDTO, or with status {@code 400 (Bad Request)} if the listCompetancies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-competancies")
    public ResponseEntity<ListCompetanciesDTO> createListCompetancies(@Valid @RequestBody ListCompetanciesDTO listCompetanciesDTO) throws URISyntaxException {
        log.debug("REST request to save ListCompetancies : {}", listCompetanciesDTO);
        if (listCompetanciesDTO.getId() != null) {
            throw new BadRequestAlertException("A new listCompetancies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListCompetanciesDTO result = listCompetanciesService.save(listCompetanciesDTO);
        return ResponseEntity.created(new URI("/api/list-competancies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-competancies} : Updates an existing listCompetancies.
     *
     * @param listCompetanciesDTO the listCompetanciesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listCompetanciesDTO,
     * or with status {@code 400 (Bad Request)} if the listCompetanciesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listCompetanciesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-competancies")
    public ResponseEntity<ListCompetanciesDTO> updateListCompetancies(@Valid @RequestBody ListCompetanciesDTO listCompetanciesDTO) throws URISyntaxException {
        log.debug("REST request to update ListCompetancies : {}", listCompetanciesDTO);
        if (listCompetanciesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListCompetanciesDTO result = listCompetanciesService.save(listCompetanciesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listCompetanciesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-competancies} : get all the listCompetancies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listCompetancies in body.
     */
    @GetMapping("/list-competancies")
    public List<ListCompetanciesDTO> getAllListCompetancies() {
        log.debug("REST request to get all ListCompetancies");
        return listCompetanciesService.findAll();
    }

    /**
     * {@code GET  /list-competancies/:id} : get the "id" listCompetancies.
     *
     * @param id the id of the listCompetanciesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listCompetanciesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-competancies/{id}")
    public ResponseEntity<ListCompetanciesDTO> getListCompetancies(@PathVariable Long id) {
        log.debug("REST request to get ListCompetancies : {}", id);
        Optional<ListCompetanciesDTO> listCompetanciesDTO = listCompetanciesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listCompetanciesDTO);
    }

    /**
     * {@code DELETE  /list-competancies/:id} : delete the "id" listCompetancies.
     *
     * @param id the id of the listCompetanciesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-competancies/{id}")
    public ResponseEntity<Void> deleteListCompetancies(@PathVariable Long id) {
        log.debug("REST request to delete ListCompetancies : {}", id);
        listCompetanciesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
