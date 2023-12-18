package ecourse.base.classSchedule;

import ecourse.base.classSchedule.domain.ClassSchedule;

public class ClassSheduleMapper {

    public static ClassScheduelDto toDTO(ClassSchedule classSchedule) {
        return new ClassScheduelDto(classSchedule.title().classTitle(), classSchedule.duration(), classSchedule.date().toString());
    }
}
