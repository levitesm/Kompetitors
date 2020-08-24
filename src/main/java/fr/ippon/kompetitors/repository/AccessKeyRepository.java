package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.AccessKey;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccessKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {

}
