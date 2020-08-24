package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListIndustries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListIndustries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListIndustriesRepository extends JpaRepository<ListIndustries, Long> {

}
