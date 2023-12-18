package ecourse.base.classSchedule.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.classSchedule.ClassScheduelDto;
import ecourse.base.classSchedule.ClassSheduleMapper;
import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.usermanagement.domain.BaseRoles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleClassController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ScheduleClassService svc = new ScheduleClassService();
    private final ClassSheduleMapper mapper = new ClassSheduleMapper();

    public ClassSchedule instanceNewClassSchedule(String title, int duration, LocalDateTime date){
        verifyUserAuthorization();
        ClassSchedule classSchedule = new ClassSchedule(title, duration, date);
        return classSchedule;
    }

    public List<ClassScheduelDto> classListToDto(List<ClassSchedule> classList){
        verifyUserAuthorization();
        List<ClassScheduelDto> classDtoList = new ArrayList<>();
        for (ClassSchedule oClass : classList) {
            classDtoList.add(mapper.toDTO(oClass));
        }
        return classDtoList;
    }

    public List<ClassSchedule> getAllScheduledClasses(){
        verifyUserAuthorization();
        return svc.getAllScheduledClasses();
    }

    public boolean createClassSchedule(ClassSchedule oClass) {
        verifyUserAuthorization();
        svc.createClassSchedule(oClass);

        return true;
    }

    private void verifyUserAuthorization(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.TEACHER);
    }
}
