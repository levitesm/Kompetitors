package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.domain.Dialogs;
import fr.ippon.kompetitors.repository.DialogsRepository;
import fr.ippon.kompetitors.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.ippon.kompetitors.domain.Dialogs}.
 */
@RestController
@RequestMapping("/api")
public class DialogsResource {

    private final Logger log = LoggerFactory.getLogger(DialogsResource.class);

    private static final String ENTITY_NAME = "dialogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DialogsRepository dialogsRepository;

    public DialogsResource(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
    }

    /**
     * {@code POST  /dialogs} : Create a new dialogs.
     *
     * @param dialogs the dialogs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dialogs, or with status {@code 400 (Bad Request)} if the dialogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dialogs")
    public ResponseEntity<Dialogs> createDialogs(@Valid @RequestBody Dialogs dialogs) throws URISyntaxException {
        log.debug("REST request to save Dialogs : {}", dialogs);
        if (dialogs.getId() != null) {
            throw new BadRequestAlertException("A new dialogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dialogs result = dialogsRepository.save(dialogs);
        return ResponseEntity.created(new URI("/api/dialogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dialogs} : Updates an existing dialogs.
     *
     * @param dialogs the dialogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dialogs,
     * or with status {@code 400 (Bad Request)} if the dialogs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dialogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dialogs")
    public ResponseEntity<Dialogs> updateDialogs(@Valid @RequestBody Dialogs dialogs) throws URISyntaxException {
        log.debug("REST request to update Dialogs : {}", dialogs);
        if (dialogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dialogs result = dialogsRepository.save(dialogs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dialogs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dialogs} : get all the dialogs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dialogs in body.
     */
    @GetMapping("/dialogs")
    public List<Dialogs> getAllDialogs() {
        log.debug("REST request to get all Dialogs");
        return dialogsRepository.findAll();
    }

    /**
     * {@code GET  /dialogs/:id} : get the "id" dialogs.
     *
     * @param id the id of the dialogs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dialogs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dialogs/{id}")
    public ResponseEntity<Dialogs> getDialogs(@PathVariable Long id) {
        log.debug("REST request to get Dialogs : {}", id);
        Optional<Dialogs> dialogs = dialogsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dialogs);
    }

    /**
     * {@code DELETE  /dialogs/:id} : delete the "id" dialogs.
     *
     * @param id the id of the dialogs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dialogs/{id}")
    public ResponseEntity<Void> deleteDialogs(@PathVariable Long id) {
        log.debug("REST request to delete Dialogs : {}", id);
        dialogsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/dialogs/compid/{compId}/section/{section}")
    public List<Dialogs> getDialogsByCompIdAndSection(@PathVariable String compId, @PathVariable String section) {
        log.debug("REST request to get Dialogs by CompId and Section");
        return dialogsRepository.getDialogsByCompetitorsIdAndSection(Long.parseLong(compId), section);
    }

    @PostMapping("/dialogs/attachments/{path}")
    public ResponseEntity<Void> createFile(@PathVariable String path, @Valid @RequestBody MultipartFile file) throws URISyntaxException {
        log.debug("REST request to save File : {}", path);
        String base = DialogsResource.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        log.info("BASE - " + base);
        if (base.lastIndexOf("/target") >=0 ) {
            base = base.substring(0,base.lastIndexOf("/target"));
        } else {
            base = "/home/jhipster";
        }
        base += "/attachments/";
        log.info("BASE - " + base);
        try {
            File directory = new File(base + path.substring(0,path.indexOf("$$$")));
            log.info("Saving file {} to directory: {}",path, directory);
            if (!directory.exists()){
                directory.mkdir();
            }

            File newFile = new File(directory + File.separator + path.substring(path.indexOf("$$$")+3, path.length()));
            log.info("Uploading file to the path: {}", newFile.getAbsolutePath());
            file.transferTo(newFile);

        } catch (Exception e) {
            log.error("Failed to save file: {}", path, e);
            e.printStackTrace();
        }

        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, base + path)).build();
    }
}
