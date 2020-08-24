package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListCities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListCities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListCitiesRepository extends JpaRepository<ListCities, Long> {

}
