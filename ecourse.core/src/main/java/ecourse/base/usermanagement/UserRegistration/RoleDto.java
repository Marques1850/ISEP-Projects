package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.infrastructure.authz.domain.model.Role;

public class RoleDto {

    private String roleName;

    public RoleDto(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public static RoleDto fromRole(Role role) {
        return new RoleDto(role.toString());
    }

    public Role toRole() {
        return Role.valueOf(roleName);
    }

    public String toString() {
        return this.roleName;
    }
}
