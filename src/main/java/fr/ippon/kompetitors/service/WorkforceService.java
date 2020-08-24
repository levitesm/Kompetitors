package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.Workforce;
import fr.ippon.kompetitors.repository.WorkforceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Workforce}.
 */
@Service
@Transactional
public class WorkforceService {

    private final Logger log = LoggerFactory.getLogger(WorkforceService.class);

    private final WorkforceRepository workforceRepository;

    public WorkforceService(WorkforceRepository workforceRepository) {
        this.workforceRepository = workforceRepository;
    }

    /**
     * Save a workforce.
     *
     * @param workforce the entity to save.
     * @return the persisted entity.
     */
    public Workforce save(Workforce workforce) {
        log.debug("Request to save Workforce : {}", workforce);
        return workforceRepository.save(workforce);
    }

    /**
     * Get all the workforces.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Workforce> findAll() {
        log.debug("Request to get all Workforces");
        return workforceRepository.findAll();
    }

    /**
     * Get workforces by competitor_id.
     *
     * @return the list of entities.
     * @param competitorId
     */
    public List<Workforce> findByCompetitorId(Long competitorId) {
        log.debug("Request to get Workforces by competitor_id");
        return workforceRepository.findByCompetitorId(competitorId);
    }

    /**
     * Get one workforce by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Workforce> findOne(Long id) {
        log.debug("Request to get Workforce : {}", id);
        return workforceRepository.findById(id);
    }

    /**
     * Delete the workforce by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Workforce : {}", id);
        workforceRepository.deleteById(id);
    }
}
