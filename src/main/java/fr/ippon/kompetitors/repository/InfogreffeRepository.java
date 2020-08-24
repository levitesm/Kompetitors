package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Infogreffe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Infogreffe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfogreffeRepository extends JpaRepository<Infogreffe, Long> {

}
