package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.Dialogs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Dialogs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DialogsRepository extends JpaRepository<Dialogs, Long> {

    public List<Dialogs> getDialogsByCompetitorsIdAndSection(Long compId, String section);

}
