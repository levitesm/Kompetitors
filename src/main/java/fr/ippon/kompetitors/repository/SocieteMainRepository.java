package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.SocieteMain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SocieteMain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocieteMainRepository extends JpaRepository<SocieteMain, Long> {

}
