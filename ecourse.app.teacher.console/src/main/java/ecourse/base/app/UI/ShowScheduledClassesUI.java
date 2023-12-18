package ecourse.base.app.UI;

import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.classSchedule.ClassScheduelDto;
import ecourse.base.classSchedule.application.ScheduleClassController;
import ecourse.base.classSchedule.domain.ClassSchedule;

import java.util.List;


public class ShowScheduledClassesUI extends AbstractUI {
    @Override
    protected boolean doShow() {
        ScheduleClassController controller = new ScheduleClassController();
        List<ClassSchedule> classList = controller.getAllScheduledClasses();
        List<ClassScheduelDto> dtoList = controller.classListToDto(classList);

        for (ClassScheduelDto dto : dtoList) {
            System.out.println("Title: " + dto.title() + "\nDuration: " + dto.duration() + "\nDate: " + dto.date());
        }

        return false;
    }

    @Override
    public String headline() {
        return "Scheduled Classes";
    }
}
