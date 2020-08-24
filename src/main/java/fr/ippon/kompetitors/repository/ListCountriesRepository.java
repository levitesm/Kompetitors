package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListCountries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListCountries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListCountriesRepository extends JpaRepository<ListCountries, Long> {
    ListCountries findOneByValue(String name);
}
