package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.People;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the People entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {

    List<People> getAllByCompetitorsId(Long id);
    List<People> getAllByCompetitorsIdAndTitle(Long id, String title);
    List<People> getAllBySpecificOffice(Long id);
    List<People> getAllBySpecificOfficeAndTitle(Long id, String title);
}
