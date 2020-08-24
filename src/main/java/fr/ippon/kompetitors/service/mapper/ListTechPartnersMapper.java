package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.ListTechPartnersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListTechPartners} and its DTO {@link ListTechPartnersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListTechPartnersMapper extends EntityMapper<ListTechPartnersDTO, ListTechPartners> {



    default ListTechPartners fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListTechPartners listTechPartners = new ListTechPartners();
        listTechPartners.setId(id);
        return listTechPartners;
    }
}
