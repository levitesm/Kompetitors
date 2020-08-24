package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.PrInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the PrInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrInfoRepository extends JpaRepository<PrInfo, Long> {
    Optional<PrInfo> getFirstByCompetitorsIdOrderByDateDesc(Long competitorId);
}
