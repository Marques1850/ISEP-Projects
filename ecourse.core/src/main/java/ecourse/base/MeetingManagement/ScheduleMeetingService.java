package ecourse.base.MeetingManagement;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import ecourse.base.MeetingManagement.event.InviteMeetingEvent;
import ecourse.base.MeetingManagement.repositories.MeetingRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleMeetingService {

    private final eCourseUserRepository userRepo;
    private final MeetingRepository meetingRepo;
    private final EventPublisher publisher = InProcessPubSub.publisher();
    public ScheduleMeetingService( eCourseUserRepository userRepo, MeetingRepository meetingRepo) {
        this.userRepo = userRepo;
        this.meetingRepo = meetingRepo;
    }

    public List<eCourseSystemUser> validateParticipants(List<EmailAddress> participants) {
        List<eCourseSystemUser> users = new ArrayList<>();
        eCourseSystemUser user;
        for (EmailAddress participant : participants) {
            if (userRepo.searchUser(participant).isPresent()) {
                user = userRepo.searchUser(participant).get();
                users.add(user);
            }
        }
        return users;
    }

    public Meeting scheduleMeeting(LocalDate date, LocalTime time, int duration, List<eCourseSystemUser> participants, eCourseSystemUser owner) {
        if (date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now().plusMinutes(30))) {
            return null;
        }
        if ( duration > 0 && !date.isBefore(LocalDate.now()) &&  participants != null && participants.size() > 0 ) {
            return new Meeting( date, time, duration, participants, owner);
        }
        return null;
    }

    public boolean saveMeeting(Meeting meeting) {
        if ( meeting != null ) {
            Meeting meeting1 = meetingRepo.save(meeting);
            inviteParticipants(meeting1);
            return true;
        }
        return false;
    }
    private void inviteParticipants(Meeting meeting) {
        if ( meeting != null ) {
            for (eCourseSystemUser participant : meeting.meetingParticipants()) {
                DomainEvent event = new InviteMeetingEvent( meeting.meetingDate(),meeting.meetingTime(), meeting.meetingDuration(), participant, meeting.meetingOwner(), meeting.identity());
                publisher.publish(event);
            }
        }
    }

}
