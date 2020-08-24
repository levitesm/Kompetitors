package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListTools;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListTools entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListToolsRepository extends JpaRepository<ListTools, Long> {

}
