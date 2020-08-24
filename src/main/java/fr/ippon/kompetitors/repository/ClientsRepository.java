package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Clients;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Clients entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {

    @SuppressWarnings("all")
    @Query(value =
        "select cli.* from clients cli " +
            "      inner join offices o on o.id = cli.offices_id " +
            "where o.competitors_id = ?1",
        nativeQuery = true)
    List<Clients> findByCompetitorId(Long competitorId);


}
