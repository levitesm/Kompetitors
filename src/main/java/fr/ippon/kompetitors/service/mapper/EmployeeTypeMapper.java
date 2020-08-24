package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.EmployeeTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeType} and its DTO {@link EmployeeTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeTypeMapper extends EntityMapper<EmployeeTypeDTO, EmployeeType> {



    default EmployeeType fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeType employeeType = new EmployeeType();
        employeeType.setId(id);
        return employeeType;
    }
}
