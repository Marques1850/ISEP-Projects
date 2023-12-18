package ecourse.base.app.UI;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.classSchedule.application.ScheduleClassController;
import ecourse.base.classSchedule.application.UpdateClassScheduleController;
import ecourse.base.classSchedule.domain.ClassSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public class UpdateScheduledClassesUI extends AbstractUI {
    @Override
    protected boolean doShow() {
        UpdateClassScheduleController controller = new UpdateClassScheduleController();
        List<ClassSchedule> classList = controller.getAllScheduledClasses();


        String title = Console.readLine("Enter the title of the class you want to update:\n");
        ClassSchedule foundClass = controller.getClassScheduleByTitle(title);

        if(foundClass == null){
            System.out.println("Class not found\n");
            return false;
        }

        for(ClassSchedule c : classList){
            if(title.equals(c.title().classTitle())){
                System.out.println("Class information:\n");
                System.out.println(c);
                foundClass = c;
                break;
            }

        }

        int duration = Console.readInteger("Enter the class duration (in minutes):\n");

        LocalDate date;
        LocalTime startTime;

        while (true) {
            try {
                String classDate = Console.readLine("Enter the class date: (DD/MM/YYYY):\n");
                String[] splitDate = classDate.split("/");
                date = LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]));
                String timeString = Console.readLine("Enter the class start time (HH:MM):\n");
                String[] splitTime = timeString.split(":");
                startTime = LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format");
            }
        }

        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);

        controller.updateClassSchedule(foundClass.title().classTitle(), duration, startDateTime);

        return false;
    }

    @Override
    public String headline() {
        return "Scheduled Classes";
    }
}
