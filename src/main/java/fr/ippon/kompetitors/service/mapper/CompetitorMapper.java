package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.Competitors;
import fr.ippon.kompetitors.domain.EmployeePricing;
import fr.ippon.kompetitors.service.dto.CompetitorDTO;
import fr.ippon.kompetitors.service.dto.EmployeePricingDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link EmployeePricing} and its DTO {@link EmployeePricingDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompetitorMapper extends EntityMapper<CompetitorDTO, Competitors> {

    default Competitors fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competitors competitor = new Competitors();
        competitor.setId(id);
        return competitor;
    }
}
