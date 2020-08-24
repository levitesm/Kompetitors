package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.ListTools;
import fr.ippon.kompetitors.repository.ListToolsRepository;
import fr.ippon.kompetitors.service.dto.ListToolsDTO;
import fr.ippon.kompetitors.service.mapper.ListToolsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ListTools}.
 */
@Service
@Transactional
public class ListToolsService {

    private final Logger log = LoggerFactory.getLogger(ListToolsService.class);

    private final ListToolsRepository listToolsRepository;

    private final ListToolsMapper listToolsMapper;

    public ListToolsService(ListToolsRepository listToolsRepository, ListToolsMapper listToolsMapper) {
        this.listToolsRepository = listToolsRepository;
        this.listToolsMapper = listToolsMapper;
    }

    /**
     * Save a listTools.
     *
     * @param listToolsDTO the entity to save.
     * @return the persisted entity.
     */
    public ListToolsDTO save(ListToolsDTO listToolsDTO) {
        log.debug("Request to save ListTools : {}", listToolsDTO);
        ListTools listTools = listToolsMapper.toEntity(listToolsDTO);
        listTools = listToolsRepository.save(listTools);
        return listToolsMapper.toDto(listTools);
    }

    /**
     * Get all the listTools.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ListToolsDTO> findAll() {
        log.debug("Request to get all ListTools");
        return listToolsRepository.findAll().stream()
            .map(listToolsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one listTools by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ListToolsDTO> findOne(Long id) {
        log.debug("Request to get ListTools : {}", id);
        return listToolsRepository.findById(id)
            .map(listToolsMapper::toDto);
    }

    /**
     * Delete the listTools by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ListTools : {}", id);
        listToolsRepository.deleteById(id);
    }
}
