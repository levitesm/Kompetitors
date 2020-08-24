package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.EmployeeRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {

}
