package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListCityCountries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListCityCountries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListCityCountriesRepository extends JpaRepository<ListCityCountries, Long> {

}
