package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Workforce;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Workforce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkforceRepository extends JpaRepository<Workforce, Long> {

    List<Workforce> findByCompetitorId(Long competitorId);
}
