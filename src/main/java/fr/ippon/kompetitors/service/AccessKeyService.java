package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.AccessKey;
import fr.ippon.kompetitors.repository.AccessKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AccessKey}.
 */
@Service
@Transactional
public class AccessKeyService {

    private final Logger log = LoggerFactory.getLogger(AccessKeyService.class);

    private final AccessKeyRepository accessKeyRepository;

    public AccessKeyService(AccessKeyRepository accessKeyRepository) {
        this.accessKeyRepository = accessKeyRepository;
    }

    /**
     * Save a accessKey.
     *
     * @param accessKey the entity to save.
     * @return the persisted entity.
     */
    public AccessKey save(AccessKey accessKey) {
        log.debug("Request to save AccessKey : {}", accessKey);
        return accessKeyRepository.save(accessKey);
    }

    /**
     * Get all the accessKeys.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AccessKey> findAll() {
        log.debug("Request to get all AccessKeys");
        return accessKeyRepository.findAll();
    }


    /**
     * Get one accessKey by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccessKey> findOne(Long id) {
        log.debug("Request to get AccessKey : {}", id);
        return accessKeyRepository.findById(id);
    }

    /**
     * Delete the accessKey by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AccessKey : {}", id);
        accessKeyRepository.deleteById(id);
    }
}
