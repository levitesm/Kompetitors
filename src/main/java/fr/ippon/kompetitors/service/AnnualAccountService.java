package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.AnnualAccount;
import fr.ippon.kompetitors.repository.AnnualAccountRepository;
import fr.ippon.kompetitors.service.dto.AnnualAccountDTO;
import fr.ippon.kompetitors.service.mapper.AnnualAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AnnualAccount}.
 */
@Service
@Transactional
public class AnnualAccountService {

    private final Logger log = LoggerFactory.getLogger(AnnualAccountService.class);

    private final AnnualAccountRepository annualAccountRepository;

    private final AnnualAccountMapper annualAccountMapper;

    public AnnualAccountService(AnnualAccountRepository annualAccountRepository, AnnualAccountMapper annualAccountMapper) {
        this.annualAccountRepository = annualAccountRepository;
        this.annualAccountMapper = annualAccountMapper;
    }

    /**
     * Save a annualAccount.
     *
     * @param annualAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public AnnualAccountDTO save(AnnualAccountDTO annualAccountDTO) {
        log.debug("Request to save AnnualAccount : {}", annualAccountDTO);
        AnnualAccount annualAccount = annualAccountMapper.toEntity(annualAccountDTO);
        annualAccount = annualAccountRepository.save(annualAccount);
        return annualAccountMapper.toDto(annualAccount);
    }

    /**
     * Get all the annualAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnualAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnnualAccounts");
        return annualAccountRepository.findAll(pageable)
            .map(annualAccountMapper::toDto);
    }


    /**
     * Get one annualAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnnualAccountDTO> findOne(Long id) {
        log.debug("Request to get AnnualAccount : {}", id);
        return annualAccountRepository.findById(id)
            .map(annualAccountMapper::toDto);
    }

    /**
     * Delete the annualAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnnualAccount : {}", id);
        annualAccountRepository.deleteById(id);
    }

    /**
     * Get all the annualAccounts by siren.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnnualAccountDTO> findAllBySiren(String siren) {
        log.debug("Request to get all AnnualAccounts by siren");
        return annualAccountRepository.getAllBySiren(siren).stream()
            .map(annualAccountMapper::toDto).collect(Collectors.toList());
    }
}
