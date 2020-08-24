package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.DashboardFinance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DashboardFinance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DashboardFinanceRepository extends JpaRepository<DashboardFinance, Long> {

    List<DashboardFinance> findAllByYear(Integer year);
}
