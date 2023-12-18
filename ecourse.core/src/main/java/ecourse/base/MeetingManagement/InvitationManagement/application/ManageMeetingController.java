package ecourse.base.MeetingManagement.InvitationManagement.application;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.InvitationManagement.repositories.MeetingInviteRepository;
import ecourse.base.MeetingManagement.Meeting;
import ecourse.base.MeetingManagement.repositories.MeetingRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;

import java.util.List;

public class ManageMeetingController {

    private final MeetingRepository meetingRepository;
    private final MeetingInviteRepository meetingInviteRepository;
    private final CancelMeetingService cancelMeetingService;
    private final AuthorizationService authz;

    public ManageMeetingController() {
        this.meetingRepository = PersistenceContext.repositories().meetings();
        this.meetingInviteRepository = PersistenceContext.repositories().meetingInvites();
        this.cancelMeetingService = new CancelMeetingService(
                PersistenceContext.repositories().meetings(),
                PersistenceContext.repositories().meetingInvites()
        );
        this.authz = AuthzRegistry.authorizationService();
    }

    public List<Meeting> getAllUserMeetings() {
        EmailAddress email = authz.session().get().authenticatedUser().email();
        return meetingRepository.getAllMeetingsWithOwner(email);
    }

    public List<Meeting> getAllParticipatingMeetings() {
        EmailAddress email = authz.session().get().authenticatedUser().email();
        return meetingRepository.getAllMeetingsWithParticipant(email);
    }

    public void cancelMeeting(Meeting meeting) {
        cancelMeetingService.cancelMeeting(meeting);
    }

    public List<MeetingInvite> getAllMeetingInvitations(int meetingID) {
       return meetingInviteRepository.searchByMeetingId(meetingID);
    }
}
