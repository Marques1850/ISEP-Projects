package ecourse.base.MeetingManagement.InvitationManagement.application;

import eapli.framework.general.domain.model.EmailAddress;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.InvitationManagement.repositories.MeetingInviteRepository;
import ecourse.base.MeetingManagement.event.InviteMeetingEvent;

public class InvitationToMeetingService {
    private final MeetingInviteRepository inviteRepo;

    public InvitationToMeetingService(MeetingInviteRepository inviteRepo) {
        this.inviteRepo = inviteRepo;
    }
    public boolean saveInvitation(final InviteMeetingEvent event , EmailAddress sessionEmail) {
        MeetingInvite invite = MeetingInvite.valueOf(
                event.date(),
                event.time(),
                event.duration(),
                event.recipient().eCourseUserEmail(),
                event.sender().eCourseUserEmail(),
                event.meetingID()
        );
        try {
            inviteRepo.save(invite);
        } catch (Exception e) {
            return false;
        }
        notifyUser(sessionEmail, EmailAddress.valueOf(invite.emailContainer().recipientEmail()));
        return true;
    }

    private void notifyUser(EmailAddress sessionEmail, EmailAddress userEmail) {
        if (sessionEmail.equals(userEmail)) {
            System.out.println("You have been invited to a meeting!");
        }
    }
}
