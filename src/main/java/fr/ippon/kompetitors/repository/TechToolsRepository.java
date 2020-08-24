package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.TechTools;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TechTools entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechToolsRepository extends JpaRepository<TechTools, Long> {

    List<TechTools> findAllByCompetitorId(Long id);
}
