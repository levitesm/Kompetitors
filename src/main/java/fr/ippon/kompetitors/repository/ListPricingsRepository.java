package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListPricings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListPricings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListPricingsRepository extends JpaRepository<ListPricings, Long> {

}
