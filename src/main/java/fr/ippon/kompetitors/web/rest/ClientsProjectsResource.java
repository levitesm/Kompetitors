package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.ClientsProjects;
import fr.ippon.kompetitors.repository.ClientsProjectsRepository;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.ClientsProjects}.
 */
@RestController
@RequestMapping("/api")
public class ClientsProjectsResource {

    private final Logger log = LoggerFactory.getLogger(ClientsProjectsResource.class);

    private static final String ENTITY_NAME = "clientsProjects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientsProjectsRepository clientsProjectsRepository;

    public ClientsProjectsResource(ClientsProjectsRepository clientsProjectsRepository) {
        this.clientsProjectsRepository = clientsProjectsRepository;
    }

    /**
     * {@code POST  /clients-projects} : Create a new clientsProjects.
     *
     * @param clientsProjects the clientsProjects to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientsProjects, or with status {@code 400 (Bad Request)} if the clientsProjects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clients-projects")
    public ResponseEntity<ClientsProjects> createClientsProjects(@RequestBody ClientsProjects clientsProjects) throws URISyntaxException {
        log.debug("REST request to save ClientsProjects : {}", clientsProjects);
        if (clientsProjects.getId() != null) {
            throw new BadRequestAlertException("A new clientsProjects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientsProjects result = clientsProjectsRepository.save(clientsProjects);
        return ResponseEntity.created(new URI("/api/clients-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clients-projects} : Updates an existing clientsProjects.
     *
     * @param clientsProjects the clientsProjects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientsProjects,
     * or with status {@code 400 (Bad Request)} if the clientsProjects is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientsProjects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clients-projects")
    public ResponseEntity<ClientsProjects> updateClientsProjects(@RequestBody ClientsProjects clientsProjects) throws URISyntaxException {
        log.debug("REST request to update ClientsProjects : {}", clientsProjects);
        if (clientsProjects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientsProjects result = clientsProjectsRepository.save(clientsProjects);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientsProjects.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clients-projects} : get all the clientsProjects.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientsProjects in body.
     */
    @GetMapping("/clients-projects")
    public List<ClientsProjects> getAllClientsProjects() {
        log.debug("REST request to get all ClientsProjects");
        return clientsProjectsRepository.findAll();
    }

    /**
     * {@code GET  /clients-projects/:id} : get the "id" clientsProjects.
     *
     * @param id the id of the clientsProjects to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientsProjects, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clients-projects/{id}")
    public ResponseEntity<ClientsProjects> getClientsProjects(@PathVariable Long id) {
        log.debug("REST request to get ClientsProjects : {}", id);
        Optional<ClientsProjects> clientsProjects = clientsProjectsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clientsProjects);
    }

    /**
     * {@code DELETE  /clients-projects/:id} : delete the "id" clientsProjects.
     *
     * @param id the id of the clientsProjects to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clients-projects/{id}")
    public ResponseEntity<Void> deleteClientsProjects(@PathVariable Long id) {
        log.debug("REST request to delete ClientsProjects : {}", id);
        clientsProjectsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
