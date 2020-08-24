package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.ListServicesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListServices} and its DTO {@link ListServicesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListServicesMapper extends EntityMapper<ListServicesDTO, ListServices> {



    default ListServices fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListServices listServices = new ListServices();
        listServices.setId(id);
        return listServices;
    }
}
