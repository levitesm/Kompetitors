package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.PrInfo;
import fr.ippon.kompetitors.repository.PrInfoRepository;
import fr.ippon.kompetitors.service.dto.PrInfoDTO;
import fr.ippon.kompetitors.service.mapper.PrInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PrInfo}.
 */
@Service
@Transactional
public class PrInfoService {

    private final Logger log = LoggerFactory.getLogger(PrInfoService.class);

    private final PrInfoRepository prInfoRepository;

    private final PrInfoMapper prInfoMapper;

    public PrInfoService(PrInfoRepository prInfoRepository, PrInfoMapper prInfoMapper) {
        this.prInfoRepository = prInfoRepository;
        this.prInfoMapper = prInfoMapper;
    }

    /**
     * Save a prInfo.
     *
     * @param prInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public PrInfoDTO save(PrInfoDTO prInfoDTO) {
        log.debug("Request to save PrInfo : {}", prInfoDTO);
        PrInfo prInfo = prInfoMapper.toEntity(prInfoDTO);
        prInfo = prInfoRepository.save(prInfo);
        return prInfoMapper.toDto(prInfo);
    }

    /**
     * Get all the prInfos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrInfoDTO> findAll() {
        log.debug("Request to get all PrInfos");
        return prInfoRepository.findAll().stream()
            .map(prInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one prInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrInfoDTO> findOne(Long id) {
        log.debug("Request to get PrInfo : {}", id);
        return prInfoRepository.findById(id)
            .map(prInfoMapper::toDto);
    }

    /**
     * Delete the prInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrInfo : {}", id);
        prInfoRepository.deleteById(id);
    }

    /**
     * Get last prInfo by competitor id.
     *
     * @param competitorId the id of the competitor.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrInfoDTO> findPrInfoByCompetitorId(Long competitorId) {
        log.debug("Request to get PrInfo by competitor id: {}", competitorId);
        return prInfoRepository.getFirstByCompetitorsIdOrderByDateDesc(competitorId)
            .map(prInfoMapper::toDto);
    }
}
