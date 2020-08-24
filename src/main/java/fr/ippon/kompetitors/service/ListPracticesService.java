package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.ListPractices;
import fr.ippon.kompetitors.repository.ListPracticesRepository;
import fr.ippon.kompetitors.service.dto.ListPracticesDTO;
import fr.ippon.kompetitors.service.mapper.ListPracticesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ListPractices}.
 */
@Service
@Transactional
public class ListPracticesService {

    private final Logger log = LoggerFactory.getLogger(ListPracticesService.class);

    private final ListPracticesRepository listPracticesRepository;

    private final ListPracticesMapper listPracticesMapper;

    public ListPracticesService(ListPracticesRepository listPracticesRepository, ListPracticesMapper listPracticesMapper) {
        this.listPracticesRepository = listPracticesRepository;
        this.listPracticesMapper = listPracticesMapper;
    }

    /**
     * Save a listPractices.
     *
     * @param listPracticesDTO the entity to save.
     * @return the persisted entity.
     */
    public ListPracticesDTO save(ListPracticesDTO listPracticesDTO) {
        log.debug("Request to save ListPractices : {}", listPracticesDTO);
        ListPractices listPractices = listPracticesMapper.toEntity(listPracticesDTO);
        listPractices = listPracticesRepository.save(listPractices);
        return listPracticesMapper.toDto(listPractices);
    }

    /**
     * Get all the listPractices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ListPracticesDTO> findAll() {
        log.debug("Request to get all ListPractices");
        return listPracticesRepository.findAll().stream()
            .map(listPracticesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one listPractices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ListPracticesDTO> findOne(Long id) {
        log.debug("Request to get ListPractices : {}", id);
        return listPracticesRepository.findById(id)
            .map(listPracticesMapper::toDto);
    }

    /**
     * Delete the listPractices by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ListPractices : {}", id);
        listPracticesRepository.deleteById(id);
    }
}
