package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.ListToolsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListTools} and its DTO {@link ListToolsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListToolsMapper extends EntityMapper<ListToolsDTO, ListTools> {



    default ListTools fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListTools listTools = new ListTools();
        listTools.setId(id);
        return listTools;
    }
}
