package ecourse.base.ExamMagnament.Controllers;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.services.ListGradesStudentService;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.ExamGradeDto;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListGradesStudentController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final eCourseUserRepository repoUser = PersistenceContext.repositories().eCourseUsers();
    private final ExamRepository examRepository= PersistenceContext.repositories().exams();
    private final ListGradesStudentService service = new ListGradesStudentService();

    public HashMap<ExamDto, List<ExamGradeDto>> listGradesStudent(){
        eCourseSystemUser user = getUser();
        List<Course> courseIn= user.coursesLearning();
        List<Exam> examsIn = new ArrayList<>();
        for (Course course: courseIn) {
            examsIn.addAll(examRepository.findAllOfCourse(course.code()));
        }
        return service.listGradesStudent(user, examsIn);
    }


    public eCourseSystemUser getUser(){
        if(authz.session().isPresent()) {
            final UserSession session = authz.session().get();
            return repoUser.searchUser(session.authenticatedUser().email()).get();
        }
        throw new IllegalStateException("Session without user in it.");

    }
}
