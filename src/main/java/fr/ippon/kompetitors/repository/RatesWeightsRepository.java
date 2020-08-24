package fr.ippon.kompetitors.repository;

import fr.ippon.kompetitors.domain.RatesWeights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompetitiveRates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatesWeightsRepository extends JpaRepository<RatesWeights, Long> {

}
