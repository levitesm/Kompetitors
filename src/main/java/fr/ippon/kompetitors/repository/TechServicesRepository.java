package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.TechServices;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TechServices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechServicesRepository extends JpaRepository<TechServices, Long> {

    List<TechServices> findAllByCompetitorId(Long id);
}
