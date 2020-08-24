package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.EmployeesTypology;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the EmployeesTypology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeesTypologyRepository extends JpaRepository<EmployeesTypology, Long> {

    List<EmployeesTypology> findAllByCompetitorIdAndYear(Long competitorId, Integer year);
}
