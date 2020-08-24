package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.ListCompetanciesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListCompetancies} and its DTO {@link ListCompetanciesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListCompetanciesMapper extends EntityMapper<ListCompetanciesDTO, ListCompetancies> {



    default ListCompetancies fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListCompetancies listCompetancies = new ListCompetancies();
        listCompetancies.setId(id);
        return listCompetancies;
    }
}
