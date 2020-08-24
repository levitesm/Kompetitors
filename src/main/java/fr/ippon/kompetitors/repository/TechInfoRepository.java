package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.TechInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the TechInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechInfoRepository extends JpaRepository<TechInfo, Long> {

    List<TechInfo> findAllByCompetitorId(Long id);
}
