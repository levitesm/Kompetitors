package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListOwnerships;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListOwnerships entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListOwnershipsRepository extends JpaRepository<ListOwnerships, Long> {

}
