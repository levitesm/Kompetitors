package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.ListPractices;
import fr.ippon.kompetitors.service.dto.ListPracticesDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ListPractices} and its DTO {@link ListPracticesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListPracticesMapper extends EntityMapper<ListPracticesDTO, ListPractices> {



    default ListPractices fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListPractices listPractices = new ListPractices();
        listPractices.setId(id);
        return listPractices;
    }
}
