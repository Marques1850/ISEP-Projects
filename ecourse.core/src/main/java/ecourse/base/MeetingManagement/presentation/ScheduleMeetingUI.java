package ecourse.base.MeetingManagement.presentation;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.MeetingManagement.Meeting;
import ecourse.base.MeetingManagement.ScheduleMeetingController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleMeetingUI extends AbstractUI {
    private final ScheduleMeetingController controller = new ScheduleMeetingController();

    @Override
    protected boolean doShow() {
        try {
            LocalTime meetingTime;
            LocalDate meetingDate;
            int duration;
            List<EmailAddress> emails = new ArrayList<>();

            while (true){
                try {
                    String date = Console.readLine("Insert meeting date: (dd:mm:yyyy)");
                    String[] dateMeeting = date.split(":");
                    int day = Integer.parseInt(dateMeeting[0]);
                    int month = Integer.parseInt(dateMeeting[1]);
                    int year = Integer.parseInt(dateMeeting[2]);
                    meetingDate = LocalDate.of( year, month, day );
                    if ( meetingDate.isAfter( LocalDate.now().minusDays(1)) ) {
                        break;
                    } else {
                        System.out.println("Meeting must be scheduled after the current time!");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid time format!");
                }
            }
            while (true){
                try {
                    String time = Console.readLine("Insert meeting time: (hh:mm)");
                    String[] timeMeeting = time.split(":");
                    int hour = Integer.parseInt(timeMeeting[0]);
                    int minute = Integer.parseInt(timeMeeting[1]);
                    meetingTime = LocalTime.of(hour, minute);
                    if ( !meetingDate.isAfter( LocalDate.now() )){
                        if ( meetingTime.isAfter(LocalTime.now()) ) {
                            break;
                        } else {
                            System.out.println("Meeting must be scheduled after the current time!");
                        }
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid time format!");
                }
            }
            while (true){
                try {
                    duration = Console.readInteger("Insert meeting duration: (minutes)");
                    if ( duration >= 5 ) {
                        break;
                    } else {
                        System.out.println("Meeting must have a duration of at least 5 minutes!");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid duration format!");
                }
            }
            String answer;
            do{
                try {
                    String participantEmail = Console.readLine("Insert participant email: ");
                    emails.add(EmailAddress.valueOf(participantEmail));
                } catch (Exception e) {
                    System.out.println("Invalid email!");
                }
                answer = Console.readLine("Do you want to add another participant? (y/n)");
            } while (answer.equals("y") || answer.equals("Y"));


            System.out.println("List of participants: ");
            for (EmailAddress email : emails) {
                System.out.println(email);
            }
            String confirmation;
            while (true){
                confirmation = Console.readLine("Do you want to schedule this meeting? (y/n)");
                if (confirmation.equals("y") || confirmation.equals("Y")) {
                    break;
                }
                if (confirmation.equals("n") || confirmation.equals("N")) break;
            }
            if (confirmation.equals("n") || confirmation.equals("N")) return true;
            Meeting meeting = controller.scheduleMeeting( meetingDate,meetingTime, duration, emails);
            while (true){
                try {
                    if ( meeting == null ) {
                        System.out.println("Invalid meeting parameters!");
                        break;
                    }
                    System.out.println(meeting);
                    confirmation = Console.readLine("Do you want to confirm meeting? (y/n)");
                    if (confirmation.equals("y") || confirmation.equals("Y")) {
                        if (controller.saveMeeting(meeting)) {
                            System.out.println("Meeting saved successfully!");
                        } else {
                            System.out.println("Error saving meeting!");
                        }
                        break;
                    }
                    if (confirmation.equals("n") || confirmation.equals("N")) break;
                } catch (Exception e) {
                    System.out.println("Invalid format!");
                }
            }


        } catch (Exception e) {
            System.out.println("!error!");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Schedule Meeting";
    }
}
