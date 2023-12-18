package ecourse.base.classSchedule.application;

import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.classSchedule.repositories.ClassRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateClassScheduleService {
    private static final int WEEKS_IN_A_SEMESTER = 16;
    private final ClassRepository repo = PersistenceContext.repositories().classes();
    public boolean updateClassSchedule(String classTitle, int duration, LocalDateTime date) {
        boolean exists = checkCoincidentialClasses(date, getAllScheduledClasses(), classTitle);
        if (!exists) {
            return false;
        }
        repo.save(new ClassSchedule(classTitle, duration, date));
        System.out.println("Class updated successfully!");
        return true;
    }

    private boolean checkCoincidentialClasses(LocalDateTime date, List<ClassSchedule> existingClasses, String currentClassTitle){
        if (!existingClasses.isEmpty()) {
            for (ClassSchedule existingClass : existingClasses) {
                try {
                    if (existingClass.title().classTitle().equals(currentClassTitle)) {
                        continue;
                    }
                    LocalDateTime existingClassDate = existingClass.date();
                    for (int i = 0; i < WEEKS_IN_A_SEMESTER; i++) { // 16 amount of weeks in a semester
                        LocalDateTime startDate = existingClassDate;
                        LocalDateTime endDate = existingClassDate.plusMinutes(existingClass.duration());
                        if (date.isAfter(startDate) && date.isBefore(endDate) || date.isEqual(startDate) || date.isEqual(endDate)) {
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

    public ClassSchedule getClassScheduleByTitle(String title) {
        return repo.getClassByTitle(title);
    }
}
