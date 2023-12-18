package ecourse.base.classSchedule.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.classSchedule.ExtraClassMapper;
import ecourse.base.classSchedule.domain.ExtraClass;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.BaseRoles;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleExtraClassController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ScheduleExtraClassService service = new ScheduleExtraClassService();
    private final ExtraClassMapper mapper = new ExtraClassMapper();

    public void createExtraClass(ExtraClass extraClass) {
        verifyUserAuthorization();
        service.createExtraClass(extraClass);
    }

    public List<eCourseSystemUser> getAllStudents() {
        verifyUserAuthorization();
        return service.getAllStudents();
    }

    public ExtraClass instanceNewClassSchedule(String title, int duration, LocalDateTime date, List<eCourseSystemUser> studentList){
        verifyUserAuthorization();
        ExtraClass extraClass = new ExtraClass(title, duration, date, studentList);
        return extraClass;

    }
    private void verifyUserAuthorization(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.TEACHER);
    }
}
