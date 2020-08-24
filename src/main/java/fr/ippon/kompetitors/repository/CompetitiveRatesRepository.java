package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.CompetitiveRates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetitiveRates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitiveRatesRepository extends JpaRepository<CompetitiveRates, Long> {

    CompetitiveRates findOneByCompetitorId(Long competitiorId);

}
