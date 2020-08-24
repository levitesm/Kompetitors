package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.EmployeeSalaries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the EmployeeSalaries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSalariesRepository extends JpaRepository<EmployeeSalaries, Long> {

    List<EmployeeSalaries> getEmployeeSalariesByCompetitorId(Long id);

}
