package ecourse.base.courseManagement;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.BaseRoles;
import ecourse.base.usermanagement.domain.Course.CourseStatus;

import java.util.List;

public class ListCoursesService {
    private final CourseRepository repo;
    private final eCourseUserRepository userRepo;
    private final AuthorizationService authorizationService;
    public ListCoursesService(final AuthorizationService authz, final CourseRepository crsRepository, final eCourseUserRepository usrRepository) {
        this.authorizationService = authz;
        this.repo = crsRepository;
        this.userRepo = usrRepository;
    }
    public List<Course> allCoursesAvailable(){
        if (authorizationService.isAuthenticatedUserAuthorizedTo(BaseRoles.STUDENT)){
            return repo.findAllWithStatus( CourseStatus.OPEN_ENROLLMENT );
        }
        if (authorizationService.isAuthenticatedUserAuthorizedTo(BaseRoles.TEACHER)){
            EmailAddress email = authorizationService.session().get().authenticatedUser().email();
            eCourseSystemUser user = userRepo.searchUser(email).get();
            return user.coursesTeaching();
        }
        if (authorizationService.isAuthenticatedUserAuthorizedTo(BaseRoles.MANAGER)){
            return repo.findAllCourses();
        }
        return null;
    }
}