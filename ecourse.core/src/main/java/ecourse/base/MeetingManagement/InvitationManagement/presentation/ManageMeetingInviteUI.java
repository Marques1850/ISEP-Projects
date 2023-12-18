package ecourse.base.MeetingManagement.InvitationManagement.presentation;


import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.MeetingManagement.InvitationManagement.application.ManageMeetingInviteController;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInviteState;

import java.util.List;

public class ManageMeetingInviteUI extends AbstractUI {
    private final ManageMeetingInviteController controller = new ManageMeetingInviteController();
    @Override
    protected boolean doShow() {
        try {
            List<MeetingInvite> listInvites = controller.getAllMeetingInvitations();
            if ( listInvites == null || listInvites.isEmpty() ) {
                System.out.println("No invites available");
                return false;
            } else {
                MeetingInvite invite = displayMeetingInvitations(listInvites);
                String answer = Console.readLine("Do you want to alter the state of the meeting invites? (y/n)");
                if ( answer.equals("y") ) {
                    MeetingInviteState state = displayMeetingInvitationsStates();
                    controller.alterMeetingInviteState(invite, state);
                }
            }
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("!error!");
        }
        return false;
    }

    private MeetingInviteState displayMeetingInvitationsStates() {
        String option;
        System.out.println("1 - Accept");
        System.out.println("2 - Reject");
        do {
            option = Console.readLine("Select meeting invite state:");
        } while ( !option.equals("1") && !option.equals("2") );
        int index = Integer.parseInt(option);
        switch (index) {
            case 1:
                return MeetingInviteState.ACCEPTED;
            case 2:
                return MeetingInviteState.REJECTED;
            default:
                return null;
        }
    }

    private MeetingInvite displayMeetingInvitations(final List<MeetingInvite> listInvites) {
        String scan;
        int option;
        System.out.println("Meeting Invites:");
        System.out.println("====================================");
        int i = 0;
        for (MeetingInvite invite : listInvites) {
            System.out.printf("%d - %s\n", i, invite.toString());
            i++;
        }
        System.out.println("====================================");
        while (true){
            try {
                scan = Console.readLine("Select meeting invite:");
                option = Integer.parseInt(scan);
                if ( option < 0 || option >= listInvites.size() ) {
                    System.out.println("Invalid option");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option");
            }
        }
        return listInvites.get(option);
    }

    @Override
    public String headline() {
        return " Accept/Reject Meeting Invites ";
    }
}