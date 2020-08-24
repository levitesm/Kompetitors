package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Finance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Finance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {

}
