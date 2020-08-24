package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Legal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Legal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LegalRepository extends JpaRepository<Legal, Long> {
    List<Legal> findBySiren(String siren);
}
