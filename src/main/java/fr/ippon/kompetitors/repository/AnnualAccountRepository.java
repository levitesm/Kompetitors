package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.AnnualAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AnnualAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnualAccountRepository extends JpaRepository<AnnualAccount, Long> {

    List<AnnualAccount> getAllBySiren(String siren);
    List<AnnualAccount> getAllBySirenAndYear(String siren, Integer year);
}
