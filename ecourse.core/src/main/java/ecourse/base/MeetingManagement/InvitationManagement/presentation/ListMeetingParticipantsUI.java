package ecourse.base.MeetingManagement.InvitationManagement.presentation;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.MeetingManagement.InvitationManagement.application.ManageMeetingController;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.Meeting;

import java.util.List;

public class ListMeetingParticipantsUI extends AbstractUI {
    private final ManageMeetingController controller = new ManageMeetingController();
    @Override
    protected boolean doShow() {
        List<Meeting> listMeetings = controller.getAllParticipatingMeetings();
        if ( listMeetings == null || listMeetings.isEmpty() ) {
            System.out.println("No meetings available");
            return false;
        } else {
            Meeting meeting = displayMeetings(listMeetings);
            List<MeetingInvite> invites =  controller.getAllMeetingInvitations(meeting.identity());

            if ( invites == null || invites.isEmpty() ) {
                System.out.println("No invites available");
                return false;
            } else {
                displayMeetingInvites(invites);
            }
        }
        return false;
    }


    private Meeting displayMeetings(List<Meeting> listMeetings) {
        String scan;
        int option;
        System.out.println("Meeting:");
        System.out.println("====================================");
        int i = 0;
        for (Meeting meeting : listMeetings) {
            System.out.printf("%d - %s\n", i, meeting.toString());
            i++;
        }
        System.out.println("====================================");
        while (true){
            try {
                scan = Console.readLine("Select meeting invite:");
                option = Integer.parseInt(scan);
                if ( option < 0 || option >= listMeetings.size() ) {
                    System.out.println("Invalid option");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option");
            }
        }
        return listMeetings.get(option);
    }

    private void displayMeetingInvites( List<MeetingInvite> listInvites){
        System.out.println("Meeting Invites:");
        System.out.println("====================================");
        for (MeetingInvite invite : listInvites) {
            System.out.printf(" - "+ invite.emailContainer().recipientEmail() + " : " + invite.state() + "\n");
        }
        System.out.println("====================================");
    }
    @Override
    public String headline() {
        return " List Meeting Participants ";
    }
}
