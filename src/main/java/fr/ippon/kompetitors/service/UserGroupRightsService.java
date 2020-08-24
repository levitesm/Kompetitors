package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.Authority;
import fr.ippon.kompetitors.domain.User;
import fr.ippon.kompetitors.domain.UserGroupRights;
import fr.ippon.kompetitors.repository.UserGroupRightsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserGroupRightsService {

    private final Logger log = LoggerFactory.getLogger(UserGroupRightsService.class);

    private final UserGroupRightsRepository userGroupRightsRepository;

    public UserGroupRightsService(UserGroupRightsRepository userGroupRightsRepository) {
        this.userGroupRightsRepository = userGroupRightsRepository;
    }

    public Set<String> getUserAccessKeys(User user) {
        Set<String> authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
        return userGroupRightsRepository.getUserGroupRightsByUserGroupNameIn(authorities)
            .stream().map(userGroupRights -> userGroupRights.getAccessKey().getName()).collect(Collectors.toSet());
    }
}
