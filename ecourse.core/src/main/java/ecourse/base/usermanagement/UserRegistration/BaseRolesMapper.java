package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.infrastructure.authz.domain.model.Role;
import ecourse.base.usermanagement.domain.BaseRoles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BaseRolesMapper {

    public static BaseRolesDto toDto(BaseRoles roles) {
        Set<Role> roleSet = new HashSet<>(Arrays.asList(roles.nonUserValues()));
        roleSet.add(roles.POWER_USER);

        return new BaseRolesDto(roleSet);
    }
}
