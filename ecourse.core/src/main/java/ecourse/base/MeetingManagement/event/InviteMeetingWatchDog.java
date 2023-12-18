package ecourse.base.MeetingManagement.event;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.pubsub.EventHandler;
import ecourse.base.MeetingManagement.InvitationManagement.application.InvitationToMeetingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InviteMeetingWatchDog implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InviteMeetingWatchDog.class);
    @Override
    public void onEvent(DomainEvent domainEvent) {
        assert domainEvent instanceof InviteMeetingEvent;

        final InviteMeetingEvent event = (InviteMeetingEvent) domainEvent;

        final InvitationToMeetingController controller = new InvitationToMeetingController();
        try {
            System.out.println("Catched event!");
            controller.inviteUser(event);
        } catch (final IntegrityViolationException e) {
            LOGGER.error("Unable to invite user on invite metting event", e);
        }
    }
}
