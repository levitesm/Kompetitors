package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.ListProjectTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListProjectTypes} and its DTO {@link ListProjectTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListProjectTypesMapper extends EntityMapper<ListProjectTypesDTO, ListProjectTypes> {



    default ListProjectTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListProjectTypes listProjectTypes = new ListProjectTypes();
        listProjectTypes.setId(id);
        return listProjectTypes;
    }
}
