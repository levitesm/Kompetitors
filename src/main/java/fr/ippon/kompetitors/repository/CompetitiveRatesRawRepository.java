package fr.ippon.kompetitors.repository;

import fr.ippon.kompetitors.domain.CompetitiveRatesRaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CompetitiveRates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitiveRatesRawRepository extends JpaRepository<CompetitiveRatesRaw, Long> {

    List<CompetitiveRatesRaw> findAllByType(String type);

}
