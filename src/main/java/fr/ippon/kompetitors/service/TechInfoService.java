package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.TechInfo;
import fr.ippon.kompetitors.repository.TechInfoRepository;
import fr.ippon.kompetitors.service.dto.TechInfoDTO;
import fr.ippon.kompetitors.service.mapper.TechInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TechInfo}.
 */
@Service
@Transactional
public class TechInfoService {

    private final Logger log = LoggerFactory.getLogger(TechInfoService.class);

    private final TechInfoRepository techInfoRepository;

    private final TechInfoMapper techInfoMapper;

    public TechInfoService(TechInfoRepository techInfoRepository, TechInfoMapper techInfoMapper) {
        this.techInfoRepository = techInfoRepository;
        this.techInfoMapper = techInfoMapper;
    }

    /**
     * Save a techInfo.
     *
     * @param techInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public TechInfoDTO save(TechInfoDTO techInfoDTO) {
        log.debug("Request to save TechInfo : {}", techInfoDTO);
        TechInfo techInfo = techInfoMapper.toEntity(techInfoDTO);
        techInfo = techInfoRepository.save(techInfo);
        return techInfoMapper.toDto(techInfo);
    }

    /**
     * Get all the techInfos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechInfoDTO> findAll() {
        log.debug("Request to get all TechInfos");
        return techInfoRepository.findAll().stream()
            .map(techInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one techInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechInfoDTO> findOne(Long id) {
        log.debug("Request to get TechInfo : {}", id);
        return techInfoRepository.findById(id)
            .map(techInfoMapper::toDto);
    }

    /**
     * Delete the techInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechInfo : {}", id);
        techInfoRepository.deleteById(id);
    }

    /**
     * Get all the techInfos by competitor id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechInfoDTO> findAllByCompetitorId(Long competitorId) {
        log.debug("Request to get all TechInfos by competitor id");
        return techInfoRepository.findAllByCompetitorId(competitorId).stream()
            .map(techInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
