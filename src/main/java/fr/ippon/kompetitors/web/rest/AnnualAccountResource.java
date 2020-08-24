package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.service.AnnualAccountService;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;
import fr.ippon.kompetitors.service.dto.AnnualAccountDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.AnnualAccount}.
 */
@RestController
@RequestMapping("/api")
public class AnnualAccountResource {

    private final Logger log = LoggerFactory.getLogger(AnnualAccountResource.class);

    private static final String ENTITY_NAME = "annualAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnnualAccountService annualAccountService;

    public AnnualAccountResource(AnnualAccountService annualAccountService) {
        this.annualAccountService = annualAccountService;
    }

    /**
     * {@code POST  /annual-accounts} : Create a new annualAccount.
     *
     * @param annualAccountDTO the annualAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annualAccountDTO, or with status {@code 400 (Bad Request)} if the annualAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/annual-accounts")
    public ResponseEntity<AnnualAccountDTO> createAnnualAccount(@Valid @RequestBody AnnualAccountDTO annualAccountDTO) throws URISyntaxException {
        log.debug("REST request to save AnnualAccount : {}", annualAccountDTO);
        if (annualAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new annualAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnnualAccountDTO result = annualAccountService.save(annualAccountDTO);
        return ResponseEntity.created(new URI("/api/annual-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /annual-accounts} : Updates an existing annualAccount.
     *
     * @param annualAccountDTO the annualAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annualAccountDTO,
     * or with status {@code 400 (Bad Request)} if the annualAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the annualAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/annual-accounts")
    public ResponseEntity<AnnualAccountDTO> updateAnnualAccount(@Valid @RequestBody AnnualAccountDTO annualAccountDTO) throws URISyntaxException {
        log.debug("REST request to update AnnualAccount : {}", annualAccountDTO);
        if (annualAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnnualAccountDTO result = annualAccountService.save(annualAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annualAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /annual-accounts} : get all the annualAccounts.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annualAccounts in body.
     */
    @GetMapping("/annual-accounts")
    public ResponseEntity<List<AnnualAccountDTO>> getAllAnnualAccounts(Pageable pageable) {
        log.debug("REST request to get a page of AnnualAccounts");
        Page<AnnualAccountDTO> page = annualAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /annual-accounts/:id} : get the "id" annualAccount.
     *
     * @param id the id of the annualAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annualAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/annual-accounts/{id}")
    public ResponseEntity<AnnualAccountDTO> getAnnualAccount(@PathVariable Long id) {
        log.debug("REST request to get AnnualAccount : {}", id);
        Optional<AnnualAccountDTO> annualAccountDTO = annualAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(annualAccountDTO);
    }

    /**
     * {@code DELETE  /annual-accounts/:id} : delete the "id" annualAccount.
     *
     * @param id the id of the annualAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/annual-accounts/{id}")
    public ResponseEntity<Void> deleteAnnualAccount(@PathVariable Long id) {
        log.debug("REST request to delete AnnualAccount : {}", id);
        annualAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /annual-accounts/siren/{siren}} : get all the annualAccounts by siren.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annualAccounts in body.
     */
    @GetMapping("/annual-accounts/siren/{siren}")
    public ResponseEntity<List<AnnualAccountDTO>> getAnnualAccountDataBySiren(@PathVariable String siren) {
        log.debug("REST request to get all AnnualAccounts by siren {}", siren);
        return ResponseEntity.ok(annualAccountService.findAllBySiren(siren));
    }
}
