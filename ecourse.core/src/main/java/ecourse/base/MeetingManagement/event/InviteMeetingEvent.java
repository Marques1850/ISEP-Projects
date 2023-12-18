package ecourse.base.MeetingManagement.event;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import java.time.LocalDate;
import java.time.LocalTime;

public class InviteMeetingEvent extends DomainEventBase implements DomainEvent {
    private final LocalDate date;
    private final LocalTime time;
    private final int duration;
    private final eCourseSystemUser recipient;
    private final eCourseSystemUser sender;
    private final int meetingID;

    public InviteMeetingEvent( LocalDate date, LocalTime time, int duration, eCourseSystemUser recipient, eCourseSystemUser sender, int meetingID) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.recipient = recipient;
        this.sender = sender;
        this.meetingID = meetingID;
    }

    public LocalDate date() {
        return date;
    }
    public LocalTime time() {
        return time;
    }
    public eCourseSystemUser recipient() {
        return recipient;
    }
    public eCourseSystemUser sender() {
        return sender;
    }
    public int duration() {
        return duration;
    }
    public int meetingID() {
        return meetingID;
    }

    @Override
    public String toString() {
        return "InviteMeetingEvent{" +
                "time=" + time +
                ", duration=" + duration +
                ", recipient=" + recipient +
                ", sender=" + sender +
                '}';
    }
}
