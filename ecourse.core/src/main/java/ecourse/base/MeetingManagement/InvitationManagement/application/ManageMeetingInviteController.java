package ecourse.base.MeetingManagement.InvitationManagement.application;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInviteState;
import ecourse.base.MeetingManagement.InvitationManagement.repositories.MeetingInviteRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;

import java.util.List;

public class ManageMeetingInviteController {
    private final MeetingInviteRepository invitesRepo;
    private final AuthorizationService authz;

    public ManageMeetingInviteController(){
        this.authz = AuthzRegistry.authorizationService();
        this.invitesRepo = PersistenceContext.repositories().meetingInvites();
    }

    public List<MeetingInvite> getAllMeetingInvitations(){
        EmailAddress email = authz.session().get().authenticatedUser().email();
        return invitesRepo.getAllMeetingInvitationsWithRecipient(email);
    }

    public void alterMeetingInviteState(MeetingInvite meetingInvite, MeetingInviteState inviteState ){
        invitesRepo.updateMeetingInviteState(meetingInvite, inviteState);
    }
}
