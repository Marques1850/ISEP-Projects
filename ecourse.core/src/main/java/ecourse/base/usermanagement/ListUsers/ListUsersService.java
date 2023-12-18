package ecourse.base.usermanagement.ListUsers;

import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.eCourseSystemUser.eCourseSystemUserMapper;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.ArrayList;
import java.util.List;

public class ListUsersService {

    private final eCourseUserRepository userRep = PersistenceContext.repositories().eCourseUsers();

    public List<eCourseSystemUserDto> findAllUsers() {
        List<eCourseSystemUser> users = userRep.listSystemUsers();
        eCourseSystemUserMapper mapper = new eCourseSystemUserMapper();
        List<eCourseSystemUserDto> dtos = new ArrayList<>();
        for (eCourseSystemUser user: users) {
            dtos.add(mapper.toDto(user));
        }
        return dtos;
    }
}
