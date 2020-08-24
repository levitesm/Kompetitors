package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListProjectTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListProjectTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListProjectTypesRepository extends JpaRepository<ListProjectTypes, Long> {

}
