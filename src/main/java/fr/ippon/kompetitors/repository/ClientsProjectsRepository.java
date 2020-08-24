package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.ClientsProjects;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientsProjects entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientsProjectsRepository extends JpaRepository<ClientsProjects, Long> {

}
