package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Updatehistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Updatehistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UpdatehistoryRepository extends JpaRepository<Updatehistory, Long> {

}
