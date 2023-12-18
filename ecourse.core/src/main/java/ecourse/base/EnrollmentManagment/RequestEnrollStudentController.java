package ecourse.base.EnrollmentManagment;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import ecourse.base.EnrollmentManagment.repositories.EnrollmentRepository;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.*;

import java.util.ArrayList;
import java.util.List;

public class RequestEnrollStudentController {
    private final CourseRepository repo = PersistenceContext.repositories().courses();
    private final eCourseUserRepository repoUser = PersistenceContext.repositories().eCourseUsers();
    private final EnrollmentRepository repoEnroll = PersistenceContext.repositories().enrollments();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RequestEnrollStudentService service = new RequestEnrollStudentService();

    public List<CourseDto> listCoursesEnrollmentOpen(){
        List<Course> courses = repo.findAllWithStatus(CourseStatus.OPEN_ENROLLMENT);
        List<CourseDto> coursesDto = new ArrayList<>();
        courseMapper courseMapper=new courseMapper();
        for (Course course : courses) {
            coursesDto.add(courseMapper.CoursetoDto(course));
        }
        return coursesDto;
    }

  public Boolean requestEnrollStudent(String courseCode) {
        Course course= repo.findByCode(CourseCode.valueOf(courseCode)).get();

        return service.requestEnrollStudent(course,getUserMeca(),repoEnroll);
    }

    public String getUserMeca(){
        if(authz.session().isPresent()) {
            final UserSession session = authz.session().get();
            return repoUser.searchUser(session.authenticatedUser().email()).get().studentMecanographicNumber().toString();
        }
        throw new IllegalStateException("Session without user in it.");

    }


}
