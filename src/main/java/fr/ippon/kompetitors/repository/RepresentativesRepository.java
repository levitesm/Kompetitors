package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Representatives;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Representatives entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepresentativesRepository extends JpaRepository<Representatives, Long> {

    List<Representatives> findAllByCompetitorSiren(Iterable<String> var1);

}
