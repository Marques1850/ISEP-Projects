package ecourse.base.app.UI;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.classSchedule.application.ScheduleExtraClassController;
import ecourse.base.classSchedule.domain.ExtraClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleExtraClassUI extends AbstractUI {

    @Override
    protected boolean doShow() {
        ScheduleExtraClassController controller = new ScheduleExtraClassController();

        System.out.println("Select the student to schedule the class for:\n");
        controller.getAllStudents().forEach(System.out::println);

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

        ExtraClass extraClass = controller.instanceNewClassSchedule(title, duration, startDateTime, null);
        controller.createExtraClass(extraClass);



        return false;
    }

    @Override
    public String headline() {
        return "Class Scheduler";
    }
}
