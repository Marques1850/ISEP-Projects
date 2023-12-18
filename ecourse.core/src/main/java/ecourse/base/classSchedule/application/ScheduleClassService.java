package ecourse.base.classSchedule.application;

import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.classSchedule.repositories.ClassRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleClassService {

    private static final int WEEKS_IN_A_SEMESTER = 16;
    private final ClassRepository repo = PersistenceContext.repositories().classes();

    public boolean createClassSchedule(ClassSchedule classSchedule) {
        List<ClassSchedule> existingClasses = getAllScheduledClasses();
        boolean exists = checkCoincidentialClasses(classSchedule, existingClasses);
        if (!exists) {
            return false;
        }
        System.out.println(classSchedule);
        repo.save(classSchedule);
        System.out.println("Class saved successfully\n");
        return true;
    }

    private boolean checkCoincidentialClasses(ClassSchedule classSchedule, List<ClassSchedule> existingClasses){
        if (!existingClasses.isEmpty()) {
            for (ClassSchedule existingClass : existingClasses) {
                try {
                    if (existingClass.title().classTitle().equals(classSchedule.title().classTitle())) {
                        throw new IllegalArgumentException("Class with this title already exists. Not saved!");
                    }

                    LocalDateTime existingClassDate = existingClass.date();
                    for (int i = 0; i < WEEKS_IN_A_SEMESTER; i++) { // 16 amount of weeks in a semester
                        LocalDateTime startDate = existingClassDate;
                        LocalDateTime endDate = existingClassDate.plusMinutes(existingClass.duration());
                        if (classSchedule.date().isAfter(startDate) && classSchedule.date().isBefore(endDate)
                                || classSchedule.date().isEqual(startDate) || classSchedule.date().isEqual(endDate)) {
                            throw new IllegalArgumentException("Class with this date already exists. Not saved!");
                        }
                        existingClassDate.plusDays(7);
                    }
                }catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }
    public List<ClassSchedule> getAllScheduledClasses(){
        return repo.getAll();
    }
}
