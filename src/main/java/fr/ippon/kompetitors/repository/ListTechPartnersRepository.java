package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ListTechPartners;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListTechPartners entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListTechPartnersRepository extends JpaRepository<ListTechPartners, Long> {

}
