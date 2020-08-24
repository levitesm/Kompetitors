package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.RegionList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegionList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionListRepository extends JpaRepository<RegionList, Long> {

}
