package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.ExternalUrls;
import fr.ippon.kompetitors.repository.ExternalUrlsRepository;
import fr.ippon.kompetitors.service.dto.ExternalUrlsDTO;
import fr.ippon.kompetitors.service.mapper.ExternalUrlsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExternalUrls}.
 */
@Service
@Transactional
public class ExternalUrlsService {

    private final Logger log = LoggerFactory.getLogger(ExternalUrlsService.class);

    private final ExternalUrlsRepository externalUrlsRepository;

    private final ExternalUrlsMapper externalUrlsMapper;

    public ExternalUrlsService(ExternalUrlsRepository externalUrlsRepository, ExternalUrlsMapper externalUrlsMapper) {
        this.externalUrlsRepository = externalUrlsRepository;
        this.externalUrlsMapper = externalUrlsMapper;
    }

    /**
     * Save a externalUrls.
     *
     * @param externalUrlsDTO the entity to save.
     * @return the persisted entity.
     */
    public ExternalUrlsDTO save(ExternalUrlsDTO externalUrlsDTO) {
        log.debug("Request to save ExternalUrls : {}", externalUrlsDTO);
        ExternalUrls externalUrls = externalUrlsMapper.toEntity(externalUrlsDTO);
        externalUrls = externalUrlsRepository.save(externalUrls);
        return externalUrlsMapper.toDto(externalUrls);
    }

    /**
     * Get all the externalUrls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExternalUrlsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExternalUrls");
        return externalUrlsRepository.findAll(pageable)
            .map(externalUrlsMapper::toDto);
    }


    /**
     * Get one externalUrls by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExternalUrlsDTO> findOne(Long id) {
        log.debug("Request to get ExternalUrls : {}", id);
        return externalUrlsRepository.findById(id)
            .map(externalUrlsMapper::toDto);
    }

    /**
     * Delete the externalUrls by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExternalUrls : {}", id);
        externalUrlsRepository.deleteById(id);
    }

    /**
     * Get one externalUrls by competitor id.
     *
     * @param id the id of the competitor.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExternalUrlsDTO> findByCompetitorId(Long id) {
        log.debug("Request to get ExternalUrls by competitor id : {}", id);
        return externalUrlsRepository.findFirstByCompetitorId(id)
            .map(externalUrlsMapper::toDto);
    }
}
