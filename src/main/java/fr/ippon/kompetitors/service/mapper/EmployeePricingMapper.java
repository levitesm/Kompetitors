package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.EmployeePricingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeePricing} and its DTO {@link EmployeePricingDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeRoleMapper.class, CompetitorMapper.class})
public interface EmployeePricingMapper extends EntityMapper<EmployeePricingDTO, EmployeePricing> {

    @Mapping(source = "employeeRole.id", target = "employeeRoleId")
    @Mapping(source = "competitor.id", target = "competitorId")
    EmployeePricingDTO toDto(EmployeePricing employeePricing);

    @Mapping(source = "employeeRoleId", target = "employeeRole")
    @Mapping(source = "competitorId", target = "competitor")
    EmployeePricing toEntity(EmployeePricingDTO employeePricingDTO);

    default EmployeePricing fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeePricing employeePricing = new EmployeePricing();
        employeePricing.setId(id);
        return employeePricing;
    }
}
