package ecourse.base.classSchedule.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.usermanagement.domain.BaseRoles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UpdateClassScheduleController {
    private final UpdateClassScheduleService service = new UpdateClassScheduleService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public void updateClassSchedule(String classTitle, int duration, LocalDateTime date) {
        verifyUserAuthorization();
        service.updateClassSchedule(classTitle, duration, date);
    }

    public ClassSchedule getClassScheduleByTitle(String title) {
        verifyUserAuthorization();
        return service.getClassScheduleByTitle(title);
    }

    public List<ClassSchedule> getAllScheduledClasses(){
        verifyUserAuthorization();
        return service.getAllScheduledClasses();
    }

    private void verifyUserAuthorization(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.TEACHER);
    }


}
