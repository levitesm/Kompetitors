package fr.ippon.kompetitors.repository;

import fr.ippon.kompetitors.domain.TechPractices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TechPractices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechPracticesRepository extends JpaRepository<TechPractices, Long> {

    List<TechPractices> findAllByCompetitorId(Long id);
}
