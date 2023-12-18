package ecourse.base.classSchedule.application;

import ecourse.base.classSchedule.domain.ExtraClass;
import ecourse.base.classSchedule.repositories.ExtraClassRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.ArrayList;
import java.util.List;

public class ScheduleExtraClassService {

    private final ExtraClassRepository extraClassRepo = PersistenceContext.repositories().extraClasses();
    private final eCourseUserRepository eCourseUserRepo = PersistenceContext.repositories().eCourseUsers();

    public boolean createExtraClass(ExtraClass extraClass) {
        System.out.println(extraClass);
        extraClassRepo.save(extraClass);
        return true;
    }

    public List<eCourseSystemUser> getAllStudents() {
        List<eCourseSystemUser> studentList = new ArrayList<>();
        List<eCourseSystemUser> userList = eCourseUserRepo.listSystemUsers();
        for (eCourseSystemUser user : userList) {
            if (user.eCourseUserRole().equals("STUDENT")) {
                userList.add(user);
            }
        }
        return studentList;
    }
}
