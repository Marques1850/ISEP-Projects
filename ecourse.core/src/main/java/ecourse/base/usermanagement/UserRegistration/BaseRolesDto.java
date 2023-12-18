package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.infrastructure.authz.domain.model.Role;
import ecourse.base.usermanagement.domain.BaseRoles;

import java.util.Set;

public class BaseRolesDto {

    private boolean isPowerUser;
    private boolean isAdmin;
    private boolean isTeacher;
    private boolean isStudent;
    private boolean isManager;

    public BaseRolesDto(final Set<Role> roles) {
        this.isPowerUser = roles.contains(BaseRoles.POWER_USER);
        this.isAdmin = roles.contains(BaseRoles.ADMIN);
        this.isTeacher = roles.contains(BaseRoles.TEACHER);
        this.isStudent = roles.contains(BaseRoles.STUDENT);
        this.isManager = roles.contains(BaseRoles.MANAGER);
    }

    public boolean isPowerUser() {
        return isPowerUser;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public boolean isManager() {
        return isManager;
    }
}
