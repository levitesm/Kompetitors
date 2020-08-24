package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.TechCompetancies;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TechCompetancies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechCompetanciesRepository extends JpaRepository<TechCompetancies, Long> {

    List<TechCompetancies> findAllByCompetitorId(Long id);
}
