package ecourse.base.courseManagement;

import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.Course.Course;

import java.util.List;

public class ListCoursesController {
    private ListCoursesService srv;

    public ListCoursesController() {
        srv = new ListCoursesService(   AuthzRegistry.authorizationService(),
                PersistenceContext.repositories().courses(),
                PersistenceContext.repositories().eCourseUsers()
        );
    }

    public List<Course> listCoursesAvailable(){
        return srv.allCoursesAvailable();
    }
}
