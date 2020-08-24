package fr.ippon.kompetitors.web.rest.vm;

import fr.ippon.kompetitors.service.dto.UserDTO;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
