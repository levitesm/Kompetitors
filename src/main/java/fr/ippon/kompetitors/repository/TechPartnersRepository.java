package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.TechPartners;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TechPartners entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechPartnersRepository extends JpaRepository<TechPartners, Long> {

    List<TechPartners> findAllByCompetitorId(Long id);
}
