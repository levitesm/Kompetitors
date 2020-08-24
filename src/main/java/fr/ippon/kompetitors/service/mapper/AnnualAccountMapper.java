package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.AnnualAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnnualAccount} and its DTO {@link AnnualAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnnualAccountMapper extends EntityMapper<AnnualAccountDTO, AnnualAccount> {



    default AnnualAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnnualAccount annualAccount = new AnnualAccount();
        annualAccount.setId(id);
        return annualAccount;
    }
}
