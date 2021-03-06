package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.RegionZipList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegionZipList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionZipListRepository extends JpaRepository<RegionZipList, Long> {

}
