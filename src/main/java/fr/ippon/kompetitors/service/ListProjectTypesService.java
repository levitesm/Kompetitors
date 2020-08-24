package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.ListProjectTypes;
import fr.ippon.kompetitors.repository.ListProjectTypesRepository;
import fr.ippon.kompetitors.service.dto.ListProjectTypesDTO;
import fr.ippon.kompetitors.service.mapper.ListProjectTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ListProjectTypes}.
 */
@Service
@Transactional
public class ListProjectTypesService {

    private final Logger log = LoggerFactory.getLogger(ListProjectTypesService.class);

    private final ListProjectTypesRepository listProjectTypesRepository;

    private final ListProjectTypesMapper listProjectTypesMapper;

    public ListProjectTypesService(ListProjectTypesRepository listProjectTypesRepository, ListProjectTypesMapper listProjectTypesMapper) {
        this.listProjectTypesRepository = listProjectTypesRepository;
        this.listProjectTypesMapper = listProjectTypesMapper;
    }

    /**
     * Save a listProjectTypes.
     *
     * @param listProjectTypesDTO the entity to save.
     * @return the persisted entity.
     */
    public ListProjectTypesDTO save(ListProjectTypesDTO listProjectTypesDTO) {
        log.debug("Request to save ListProjectTypes : {}", listProjectTypesDTO);
        ListProjectTypes listProjectTypes = listProjectTypesMapper.toEntity(listProjectTypesDTO);
        listProjectTypes = listProjectTypesRepository.save(listProjectTypes);
        return listProjectTypesMapper.toDto(listProjectTypes);
    }

    /**
     * Get all the listProjectTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ListProjectTypesDTO> findAll() {
        log.debug("Request to get all ListProjectTypes");
        return listProjectTypesRepository.findAll().stream()
            .map(listProjectTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one listProjectTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ListProjectTypesDTO> findOne(Long id) {
        log.debug("Request to get ListProjectTypes : {}", id);
        return listProjectTypesRepository.findById(id)
            .map(listProjectTypesMapper::toDto);
    }

    /**
     * Delete the listProjectTypes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ListProjectTypes : {}", id);
        listProjectTypesRepository.deleteById(id);
    }
}
