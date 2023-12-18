package ecourse.base.app.UI;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.classSchedule.application.ScheduleClassController;
import ecourse.base.classSchedule.domain.ClassSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleClassUI extends AbstractUI {

    @Override
    protected boolean doShow() {
        ScheduleClassController controller = new ScheduleClassController();

        String title = Console.readLine("Enter the class title:\n");
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

        ClassSchedule newClass = controller.instanceNewClassSchedule(title, duration, startDateTime);
        controller.createClassSchedule(newClass);

        return false;
    }

    @Override
    public String headline() {
        return "Class Scheduler";
    }
}
