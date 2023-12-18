package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.infrastructure.authz.domain.model.Role;

public class RoleMapper {

    public static RoleDto toDto(Role role) {
        RoleDto roleDto = new RoleDto(role.toString());
        roleDto.setRoleName(role.toString());
        return roleDto;
    }
}
