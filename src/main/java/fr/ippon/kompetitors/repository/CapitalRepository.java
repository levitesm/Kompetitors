package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Capital;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Capital entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapitalRepository extends JpaRepository<Capital, Long> {

    List<Capital> findAllByCompetitorSiren(Iterable<String> var1);

}
