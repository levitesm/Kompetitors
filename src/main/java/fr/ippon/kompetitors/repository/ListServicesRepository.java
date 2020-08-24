package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListServices;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListServices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListServicesRepository extends JpaRepository<ListServices, Long> {

}
