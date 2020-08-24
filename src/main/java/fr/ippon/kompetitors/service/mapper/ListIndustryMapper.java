package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.EmployeePricing;
import fr.ippon.kompetitors.domain.ListIndustries;
import fr.ippon.kompetitors.service.dto.EmployeePricingDTO;
import fr.ippon.kompetitors.service.dto.ListIndustryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link EmployeePricing} and its DTO {@link EmployeePricingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ListIndustryMapper extends EntityMapper<ListIndustryDTO, ListIndustries> {

    default ListIndustries fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListIndustries competitor = new ListIndustries();
        competitor.setId(id);
        return competitor;
    }
}
