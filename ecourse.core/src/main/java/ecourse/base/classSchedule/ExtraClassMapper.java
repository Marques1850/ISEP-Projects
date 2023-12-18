package ecourse.base.classSchedule;

import ecourse.base.classSchedule.domain.ExtraClass;

public class ExtraClassMapper {

    public static ExtraClassDto toDTO(ExtraClass ExtraClass) {
        return new ExtraClassDto(ExtraClass.title().toString(), ExtraClass.duration().duration(),
                ExtraClass.date().toString(), ExtraClass.studentList());
    }
}
