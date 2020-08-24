package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListClientsProjectTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListClientsProjectTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListClientsProjectTypesRepository extends JpaRepository<ListClientsProjectTypes, Long> {

}
