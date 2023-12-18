package ecourse.base.MeetingManagement.InvitationManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.EmailAddress;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class MeetingInvite implements AggregateRoot<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="INVITATION_DATE", nullable = false, unique = false)
    private LocalDate date;
    @Column(name="INVITATION_TIME", nullable = false, unique = false)
    private LocalTime time;
    @Column(name="INVITATION_DURATION", unique = false)
    private int duration;
    @Column(name="EMAIL_CONTAINER")
    private EmailContainer emailContainer;
    @Column(name="INVITATION_STATE", nullable = false, unique = false)
    private MeetingInviteState state;

    @Column(name="INVITATION_MEETING_ID", nullable = false, unique = false)
    private int meetingID;

    protected MeetingInvite() {
    }
    protected MeetingInvite( LocalDate date, LocalTime time, int duration, EmailAddress recipient, EmailAddress sender, int meetingID) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.emailContainer = new EmailContainer(recipient.toString(), sender.toString());
        this.state = MeetingInviteState.PENDING;
        this.meetingID = meetingID;
    }
    public static MeetingInvite valueOf( LocalDate date, LocalTime time, int duration, EmailAddress recipient, EmailAddress sender, int meetingID) {
        if (date == null || time == null || recipient == null || sender == null || meetingID == 0) {
            throw new IllegalArgumentException();
        }
        return new MeetingInvite(date,time, duration, recipient, sender, meetingID);
    }


    public void acceptInvite(){
        this.state = MeetingInviteState.ACCEPTED;
    }
    public void rejectInvite(){
        this.state = MeetingInviteState.REJECTED;
    }
    public int meetingID() {
        return meetingID;
    }

    public MeetingInviteState state() {
        return state;
    }

    public EmailContainer emailContainer() {return emailContainer;}
    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }
    @Override
    public Integer identity() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof MeetingInvite) {
            MeetingInvite other = (MeetingInvite) obj;
            return this.time.equals(other.time) &&
                    this.duration == other.duration &&
                    this.state.equals(other.state) &&
                    this.meetingID == other.meetingID;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Meeting Invite [" +
                "From: " + emailContainer.senderEmail() +
                ", "+ date +" at " + time +
                " for " + duration +
                "min. (" + state + ')' +
                ']';
    }
}
