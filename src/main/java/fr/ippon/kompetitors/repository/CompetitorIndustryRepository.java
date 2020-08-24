package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.CompetitorIndustry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CompetitorIndustry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitorIndustryRepository extends JpaRepository<CompetitorIndustry, Long> {

    List<CompetitorIndustry> findAllByCompetitorId(Long id);
}
