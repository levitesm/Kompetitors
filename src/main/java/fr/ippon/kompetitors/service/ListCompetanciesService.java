package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.ListCompetancies;
import fr.ippon.kompetitors.repository.ListCompetanciesRepository;
import fr.ippon.kompetitors.service.dto.ListCompetanciesDTO;
import fr.ippon.kompetitors.service.mapper.ListCompetanciesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ListCompetancies}.
 */
@Service
@Transactional
public class ListCompetanciesService {

    private final Logger log = LoggerFactory.getLogger(ListCompetanciesService.class);

    private final ListCompetanciesRepository listCompetanciesRepository;

    private final ListCompetanciesMapper listCompetanciesMapper;

    public ListCompetanciesService(ListCompetanciesRepository listCompetanciesRepository, ListCompetanciesMapper listCompetanciesMapper) {
        this.listCompetanciesRepository = listCompetanciesRepository;
        this.listCompetanciesMapper = listCompetanciesMapper;
    }

    /**
     * Save a listCompetancies.
     *
     * @param listCompetanciesDTO the entity to save.
     * @return the persisted entity.
     */
    public ListCompetanciesDTO save(ListCompetanciesDTO listCompetanciesDTO) {
        log.debug("Request to save ListCompetancies : {}", listCompetanciesDTO);
        ListCompetancies listCompetancies = listCompetanciesMapper.toEntity(listCompetanciesDTO);
        listCompetancies = listCompetanciesRepository.save(listCompetancies);
        return listCompetanciesMapper.toDto(listCompetancies);
    }

    /**
     * Get all the listCompetancies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ListCompetanciesDTO> findAll() {
        log.debug("Request to get all ListCompetancies");
        return listCompetanciesRepository.findAll().stream()
            .map(listCompetanciesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one listCompetancies by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ListCompetanciesDTO> findOne(Long id) {
        log.debug("Request to get ListCompetancies : {}", id);
        return listCompetanciesRepository.findById(id)
            .map(listCompetanciesMapper::toDto);
    }

    /**
     * Delete the listCompetancies by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ListCompetancies : {}", id);
        listCompetanciesRepository.deleteById(id);
    }
}
