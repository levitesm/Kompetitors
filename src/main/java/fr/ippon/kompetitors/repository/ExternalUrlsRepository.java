package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ExternalUrls;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ExternalUrls entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternalUrlsRepository extends JpaRepository<ExternalUrls, Long> {

    Optional<ExternalUrls> findFirstByCompetitorId(Long competitorId);
}
