package fr.ippon.kompetitors.repository;
import fr.ippon.kompetitors.domain.UserGroupRights;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Set;


/**
 * Spring Data  repository for the UserGroupRights entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGroupRightsRepository extends JpaRepository<UserGroupRights, Long> {

    Set<UserGroupRights> getUserGroupRightsByUserGroupNameIn(Set<String> authorities);
}
