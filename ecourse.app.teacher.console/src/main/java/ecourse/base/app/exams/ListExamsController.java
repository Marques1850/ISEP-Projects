package ecourse.base.app.exams;

import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.BaseRoles;

import java.util.List;

public class ListExamsController {

    private ListCourseExamsService srv;
    public ListExamsController() {
        this.srv = new ListCourseExamsService(
                PersistenceContext.repositories().exams(),
                PersistenceContext.repositories().courses()
        );
    }
    public List<Exam> listCourseExams(String courseID ) {
        AuthzRegistry.authorizationService().ensureAuthenticatedUserHasAnyOf(BaseRoles.TEACHER);
        return srv.allCourseExams(courseID);
    }
}
