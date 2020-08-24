package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.EmployeesTypologyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeesTypology} and its DTO {@link EmployeesTypologyDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeTypeMapper.class, CompetitorMapper.class})
public interface EmployeesTypologyMapper extends EntityMapper<EmployeesTypologyDTO, EmployeesTypology> {

    @Mapping(source = "employeeType.id", target = "employeeTypeId")
    @Mapping(source = "employeeType.name", target = "employeeTypeName")
    @Mapping(source = "competitor.id", target = "competitorId")
    EmployeesTypologyDTO toDto(EmployeesTypology employeesTypology);

    @Mapping(source = "employeeTypeId", target = "employeeType")
    @Mapping(source = "competitorId", target = "competitor")
    EmployeesTypology toEntity(EmployeesTypologyDTO employeesTypologyDTO);

    default EmployeesTypology fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeesTypology employeesTypology = new EmployeesTypology();
        employeesTypology.setId(id);
        return employeesTypology;
    }
}
