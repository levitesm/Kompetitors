package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.ListTechPartnersService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.ListTechPartnersDTO;

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
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ListTechPartners}.
 */
@RestController
@RequestMapping("/api")
public class ListTechPartnersResource {

    private final Logger log = LoggerFactory.getLogger(ListTechPartnersResource.class);

    private static final String ENTITY_NAME = "listTechPartners";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListTechPartnersService listTechPartnersService;

    public ListTechPartnersResource(ListTechPartnersService listTechPartnersService) {
        this.listTechPartnersService = listTechPartnersService;
    }

    /**
     * {@code POST  /list-tech-partners} : Create a new listTechPartners.
     *
     * @param listTechPartnersDTO the listTechPartnersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listTechPartnersDTO, or with status {@code 400 (Bad Request)} if the listTechPartners has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-tech-partners")
    public ResponseEntity<ListTechPartnersDTO> createListTechPartners(@Valid @RequestBody ListTechPartnersDTO listTechPartnersDTO) throws URISyntaxException {
        log.debug("REST request to save ListTechPartners : {}", listTechPartnersDTO);
        if (listTechPartnersDTO.getId() != null) {
            throw new BadRequestAlertException("A new listTechPartners cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListTechPartnersDTO result = listTechPartnersService.save(listTechPartnersDTO);
        return ResponseEntity.created(new URI("/api/list-tech-partners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-tech-partners} : Updates an existing listTechPartners.
     *
     * @param listTechPartnersDTO the listTechPartnersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listTechPartnersDTO,
     * or with status {@code 400 (Bad Request)} if the listTechPartnersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listTechPartnersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-tech-partners")
    public ResponseEntity<ListTechPartnersDTO> updateListTechPartners(@Valid @RequestBody ListTechPartnersDTO listTechPartnersDTO) throws URISyntaxException {
        log.debug("REST request to update ListTechPartners : {}", listTechPartnersDTO);
        if (listTechPartnersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListTechPartnersDTO result = listTechPartnersService.save(listTechPartnersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listTechPartnersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-tech-partners} : get all the listTechPartners.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listTechPartners in body.
     */
    @GetMapping("/list-tech-partners")
    public List<ListTechPartnersDTO> getAllListTechPartners() {
        log.debug("REST request to get all ListTechPartners");
        return listTechPartnersService.findAll();
    }

    /**
     * {@code GET  /list-tech-partners/:id} : get the "id" listTechPartners.
     *
     * @param id the id of the listTechPartnersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listTechPartnersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-tech-partners/{id}")
    public ResponseEntity<ListTechPartnersDTO> getListTechPartners(@PathVariable Long id) {
        log.debug("REST request to get ListTechPartners : {}", id);
        Optional<ListTechPartnersDTO> listTechPartnersDTO = listTechPartnersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listTechPartnersDTO);
    }

    /**
     * {@code DELETE  /list-tech-partners/:id} : delete the "id" listTechPartners.
     *
     * @param id the id of the listTechPartnersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-tech-partners/{id}")
    public ResponseEntity<Void> deleteListTechPartners(@PathVariable Long id) {
        log.debug("REST request to delete ListTechPartners : {}", id);
        listTechPartnersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
