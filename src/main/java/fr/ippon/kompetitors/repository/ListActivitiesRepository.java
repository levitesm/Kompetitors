package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListActivities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListActivities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListActivitiesRepository extends JpaRepository<ListActivities, Long> {

}
