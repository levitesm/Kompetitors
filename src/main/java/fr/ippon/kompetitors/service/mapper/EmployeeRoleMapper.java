package fr.ippon.kompetitors.service.mapper;

import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.service.dto.EmployeeRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeRole} and its DTO {@link EmployeeRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeRoleMapper extends EntityMapper<EmployeeRoleDTO, EmployeeRole> {



    default EmployeeRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setId(id);
        return employeeRole;
    }
}
