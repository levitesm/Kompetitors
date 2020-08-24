package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListCompetancies;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListCompetancies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListCompetanciesRepository extends JpaRepository<ListCompetancies, Long> {

}
