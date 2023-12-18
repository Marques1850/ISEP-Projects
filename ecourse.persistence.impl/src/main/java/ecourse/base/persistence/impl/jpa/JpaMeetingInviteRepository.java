package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInviteState;
import ecourse.base.MeetingManagement.InvitationManagement.repositories.MeetingInviteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JpaMeetingInviteRepository extends JpaAutoTxRepository<MeetingInvite, Integer, Integer>
        implements MeetingInviteRepository {

    public JpaMeetingInviteRepository(final TransactionalContext autoTx) { super(autoTx, "idInvite"); }

    public JpaMeetingInviteRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "idInvite");
    }


    @Override
    public List<MeetingInvite> searchByMeetingId(int meetingID) {
        Iterable<MeetingInvite> iterable = findAll();
        List<MeetingInvite> meetingInvites = new ArrayList<>();
        for (MeetingInvite meetingInvite : iterable) {
            if (meetingInvite.meetingID() == meetingID) {
                meetingInvites.add(meetingInvite);
            }
        }
        return meetingInvites;
    }

    @Override
    public List<MeetingInvite> getAllMeetingInvitationsWithRecipient(EmailAddress email) {
        Iterable<MeetingInvite> iterable = findAll();
        List<MeetingInvite> meetingInvites = new ArrayList<>();
        for (MeetingInvite meetingInvite : iterable) {
            if (meetingInvite.emailContainer().recipientEmail().equals(email.toString()) && meetingInvite.state() == MeetingInviteState.PENDING ) {
                meetingInvites.add(meetingInvite);
            }
        }
        return meetingInvites;
    }

    @Override
    public MeetingInvite updateMeetingInviteState(MeetingInvite meetingInvite, MeetingInviteState inviteState) {
        Iterable<MeetingInvite> iterable = findAll();
        MeetingInvite inviteToReturn = null;
        for (MeetingInvite currentMeetingInvite : iterable) {
            if (currentMeetingInvite.identity() == meetingInvite.identity()) {
                if (currentMeetingInvite.state() == MeetingInviteState.PENDING) {
                    if (inviteState == MeetingInviteState.ACCEPTED) {
                        currentMeetingInvite.acceptInvite();
                    } else if (inviteState == MeetingInviteState.REJECTED) {
                        currentMeetingInvite.rejectInvite();
                    }
                    inviteToReturn = currentMeetingInvite;
                    this.save(currentMeetingInvite);
                }
            }
        }
        return inviteToReturn;
    }
}
