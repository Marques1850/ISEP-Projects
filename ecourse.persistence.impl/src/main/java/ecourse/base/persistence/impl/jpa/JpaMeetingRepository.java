package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.Meeting;
import ecourse.base.MeetingManagement.repositories.MeetingRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import java.util.ArrayList;
import java.util.List;

public class JpaMeetingRepository extends JpaAutoTxRepository<Meeting, Integer, Integer>
        implements MeetingRepository {
    public JpaMeetingRepository(String puname) {
        super(puname, "id");
    }
    public JpaMeetingRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }


    @Override
    public List<Meeting> getAllMeetingsWithOwner(EmailAddress email) {
        Iterable<Meeting> iterable = findAll();
        List<Meeting> meetings = new ArrayList<>();
        for (Meeting meeting : iterable) {
            if (meeting.meetingOwner().eCourseUserEmail().equals(email)) {
                meetings.add(meeting);
            }
        }
        return meetings;
    }

    @Override
    public List<Meeting> getAllMeetingsWithParticipant(EmailAddress email){
        Iterable<Meeting> iterable = findAll();
        List<Meeting> meetings = new ArrayList<>();
        for (Meeting meeting : iterable) {
            if (meeting.meetingOwner().eCourseUserEmail().equals(email)) {
                meetings.add(meeting);
                continue;
            }
            for (eCourseSystemUser user : meeting.meetingParticipants()) {
                if (user.eCourseUserEmail().equals(email)) {
                    meetings.add(meeting);
                    break;
                }
            }
        }
        return meetings;
    }

}
