package fr.ippon.kompetitors.repository;

import fr.ippon.kompetitors.domain.ListPractices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListPractices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListPracticesRepository extends JpaRepository<ListPractices, Long> {

}
