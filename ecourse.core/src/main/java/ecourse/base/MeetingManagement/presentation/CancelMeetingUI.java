package ecourse.base.MeetingManagement.presentation;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.MeetingManagement.InvitationManagement.application.ManageMeetingController;
import ecourse.base.MeetingManagement.Meeting;

import java.util.List;

public class CancelMeetingUI extends AbstractUI {
    ManageMeetingController controller = new ManageMeetingController();

    @Override
    protected boolean doShow() {
        try {
            List<Meeting> meetingList = controller.getAllUserMeetings();
            if (meetingList == null || meetingList.isEmpty()) {
                System.out.println("No meetings available");
                return false;
            } else {
                displayMeetings(meetingList);
            }
        } catch (Exception e) {
            System.out.println("!error!");
        }
        return true;
    }

    @Override
    public String headline() {
        return "Cancel Meetings";
    }

    private void displayMeetings(List<Meeting> meetingList) {
        System.out.println("Meetings:");
        System.out.println("====================================");
        int i = 0;
        for (Meeting meeting : meetingList) {
            System.out.printf("%d - %s%n", i, meeting.toString());
            i++;
        }
        System.out.println("====================================");
        int option;
        while (true) {
            try {
                String scan = Console.readLine("Select meeting to cancel:");
                option = Integer.parseInt(scan);
                if (option >= 0 && option < meetingList.size()) {
                    System.out.println(meetingList.get(option).toString());
                }
                break;
            } catch (Exception e) {
                System.out.println("!error!");
            }
        }
        while (true) {
            try {
                String confirmation = Console.readLine("Are you sure you want to cancel this meeting? (y/n)");
                if (confirmation.equals("y")) {
                    controller.cancelMeeting(meetingList.get(option));
                    System.out.println("Meeting canceled");
                    break;
                } else if (confirmation.equals("n")) {
                    System.out.println("Meeting not canceled");
                    break;
                }
            } catch (Exception e) {
                System.out.println("!error!");
            }
        }
    }
}
