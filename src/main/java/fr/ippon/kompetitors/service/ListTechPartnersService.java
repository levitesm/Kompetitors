package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.ListTechPartners;
import fr.ippon.kompetitors.repository.ListTechPartnersRepository;
import fr.ippon.kompetitors.service.dto.ListTechPartnersDTO;
import fr.ippon.kompetitors.service.mapper.ListTechPartnersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ListTechPartners}.
 */
@Service
@Transactional
public class ListTechPartnersService {

    private final Logger log = LoggerFactory.getLogger(ListTechPartnersService.class);

    private final ListTechPartnersRepository listTechPartnersRepository;

    private final ListTechPartnersMapper listTechPartnersMapper;

    public ListTechPartnersService(ListTechPartnersRepository listTechPartnersRepository, ListTechPartnersMapper listTechPartnersMapper) {
        this.listTechPartnersRepository = listTechPartnersRepository;
        this.listTechPartnersMapper = listTechPartnersMapper;
    }

    /**
     * Save a listTechPartners.
     *
     * @param listTechPartnersDTO the entity to save.
     * @return the persisted entity.
     */
    public ListTechPartnersDTO save(ListTechPartnersDTO listTechPartnersDTO) {
        log.debug("Request to save ListTechPartners : {}", listTechPartnersDTO);
        ListTechPartners listTechPartners = listTechPartnersMapper.toEntity(listTechPartnersDTO);
        listTechPartners = listTechPartnersRepository.save(listTechPartners);
        return listTechPartnersMapper.toDto(listTechPartners);
    }

    /**
     * Get all the listTechPartners.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ListTechPartnersDTO> findAll() {
        log.debug("Request to get all ListTechPartners");
        return listTechPartnersRepository.findAll().stream()
            .map(listTechPartnersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one listTechPartners by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ListTechPartnersDTO> findOne(Long id) {
        log.debug("Request to get ListTechPartners : {}", id);
        return listTechPartnersRepository.findById(id)
            .map(listTechPartnersMapper::toDto);
    }

    /**
     * Delete the listTechPartners by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ListTechPartners : {}", id);
        listTechPartnersRepository.deleteById(id);
    }
}
