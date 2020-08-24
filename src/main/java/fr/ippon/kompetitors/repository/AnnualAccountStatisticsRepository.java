package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.AnnualAccountStatistics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AnnualAccountStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnualAccountStatisticsRepository extends JpaRepository<AnnualAccountStatistics, Long> {

    List<AnnualAccountStatistics> findAllBySiren(String siren);
}
