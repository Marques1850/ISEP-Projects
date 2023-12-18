package ecourse.base.MeetingManagement;

import eapli.framework.domain.model.AggregateRoot;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meeting implements AggregateRoot<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="MEETING_TIME", nullable = false, unique = false)
    private LocalTime time;
    @Column(name="MEETING_DATE", nullable = false, unique = false)
    private LocalDate date;
    @Column(name="MEETING_DURATION", unique = false)
    private int duration;
    @OneToMany
    @JoinColumn(name="PARTICIPANTS_LIST", unique = false)
    private List<eCourseSystemUser> participants;
    @ManyToOne
    private eCourseSystemUser owner;

    protected Meeting() {
    }

    public Meeting( LocalDate date, LocalTime time, int duration, List<eCourseSystemUser> participants, eCourseSystemUser owner) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.participants = participants;
        this.owner = owner;
    }

    public Meeting( LocalDate date ,LocalTime time, int duration, eCourseSystemUser owner) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.owner = owner;
        this.participants = new ArrayList<>();
    }


    public void addParticipants(List<eCourseSystemUser> participants){
        this.participants.addAll(participants);
    }

    public int meetingDuration() {
        return duration;
    }
    public LocalDate meetingDate() {
        return date;
    }
    public LocalTime meetingTime() {
        return time;
    }
    public eCourseSystemUser meetingOwner() {
        return owner;
    }
    public List<eCourseSystemUser> meetingParticipants() {
        return participants;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Meeting)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        return identity().equals(((Meeting) other).identity());
    }

    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof Meeting) {
            Meeting other = (Meeting) obj;
            return this.time.equals(other.meetingTime()) &&
                    this.duration == other.duration &&
                    this.participants.equals(other.meetingParticipants()) &&
                    this.owner.equals(other.meetingOwner());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Meeting by " + owner.eCourseUserEmail().toString() +" [" + date + " at " + time +
                ", for " + duration + " minutes" +
                ", participants =" + printEmails() +
                ']';
    }
    private String printEmails(){
        StringBuilder emails;
        emails = new StringBuilder();
        for (eCourseSystemUser participant : participants) {
            emails.append(participant.eCourseUserEmail().toString()).append(", ");
        }
        return emails.toString();
    }
}
