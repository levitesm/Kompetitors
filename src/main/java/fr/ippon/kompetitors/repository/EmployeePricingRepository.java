package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.EmployeePricing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the EmployeePricing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeePricingRepository extends JpaRepository<EmployeePricing, Long> {

    List<EmployeePricing> findAllByCompetitorId(Long competitorId);

}
