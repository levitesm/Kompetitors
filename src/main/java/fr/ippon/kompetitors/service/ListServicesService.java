package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.ListServices;
import fr.ippon.kompetitors.repository.ListServicesRepository;
import fr.ippon.kompetitors.service.dto.ListServicesDTO;
import fr.ippon.kompetitors.service.mapper.ListServicesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ListServices}.
 */
@Service
@Transactional
public class ListServicesService {

    private final Logger log = LoggerFactory.getLogger(ListServicesService.class);

    private final ListServicesRepository listServicesRepository;

    private final ListServicesMapper listServicesMapper;

    public ListServicesService(ListServicesRepository listServicesRepository, ListServicesMapper listServicesMapper) {
        this.listServicesRepository = listServicesRepository;
        this.listServicesMapper = listServicesMapper;
    }

    /**
     * Save a listServices.
     *
     * @param listServicesDTO the entity to save.
     * @return the persisted entity.
     */
    public ListServicesDTO save(ListServicesDTO listServicesDTO) {
        log.debug("Request to save ListServices : {}", listServicesDTO);
        ListServices listServices = listServicesMapper.toEntity(listServicesDTO);
        listServices = listServicesRepository.save(listServices);
        return listServicesMapper.toDto(listServices);
    }

    /**
     * Get all the listServices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ListServicesDTO> findAll() {
        log.debug("Request to get all ListServices");
        return listServicesRepository.findAll().stream()
            .map(listServicesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one listServices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ListServicesDTO> findOne(Long id) {
        log.debug("Request to get ListServices : {}", id);
        return listServicesRepository.findById(id)
            .map(listServicesMapper::toDto);
    }

    /**
     * Delete the listServices by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ListServices : {}", id);
        listServicesRepository.deleteById(id);
    }
}
