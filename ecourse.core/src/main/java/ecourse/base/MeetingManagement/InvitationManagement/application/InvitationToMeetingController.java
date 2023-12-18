package ecourse.base.MeetingManagement.InvitationManagement.application;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.MeetingManagement.event.InviteMeetingEvent;
import ecourse.base.infrastructure.persistence.PersistenceContext;


public class InvitationToMeetingController {
    private final InvitationToMeetingService service;
    public InvitationToMeetingController() {
        this.service = new InvitationToMeetingService(
                PersistenceContext.repositories().meetingInvites()
        );
    }
    public void inviteUser(final InviteMeetingEvent invitation) {
        EmailAddress sessionEmail = AuthzRegistry.authorizationService().session().get().authenticatedUser().email();
        service.saveInvitation(invitation, sessionEmail);
    }
}
