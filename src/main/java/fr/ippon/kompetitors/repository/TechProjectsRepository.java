package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.TechProjects;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TechProjects entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechProjectsRepository extends JpaRepository<TechProjects, Long> {

    List<TechProjects> findAllByCompetitorId(Long id);
}
