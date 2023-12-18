package ecourse.base.ExamMagnament.Controllers;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourse.base.ExamMagnament.Mapper.GradeMapper;
import ecourse.base.ExamMagnament.services.SeeTeacherCourseGradesService;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.ExamMagnament.domain.ExamGradeDto;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.ArrayList;
import java.util.List;

public class ListExamGradesCourseController {

    private final SeeTeacherCourseGradesService service = new SeeTeacherCourseGradesService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final eCourseUserRepository repo = PersistenceContext.repositories().eCourseUsers();
    private final ExamRepository repoE = PersistenceContext.repositories().exams();

    private final CourseRepository repoC = PersistenceContext.repositories().courses();




    public List<ExamGradeDto> getExamGradesOfCourse() {
        SystemUser user = getUser();
        List<ExamGrade> examGrades = service.searchUser(user,repoE,repo, repoC);
        List<ExamGradeDto> examGradeDtos=new ArrayList<>();
        for ( ExamGrade examGrade : examGrades )  {
            examGradeDtos.add(GradeMapper.toDTO(examGrade));
        }
        return examGradeDtos;
    }

    public SystemUser getUser(){
        if(authz.session().isPresent()) {
            final UserSession session = authz.session().get();
            return session.authenticatedUser();
        }
        throw new IllegalStateException("Session without user in it.");

    }


}
