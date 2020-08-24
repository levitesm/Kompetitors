package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ShareHolders;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ShareHolders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShareHoldersRepository extends JpaRepository<ShareHolders, Long> {

    List<ShareHolders> findAllByCompetitorSiren(Iterable<String> var1);

}
