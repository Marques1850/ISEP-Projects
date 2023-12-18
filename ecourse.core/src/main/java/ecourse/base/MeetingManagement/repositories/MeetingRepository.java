package ecourse.base.MeetingManagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;
import ecourse.base.MeetingManagement.Meeting;

import java.util.List;

public interface MeetingRepository extends DomainRepository<Integer, Meeting> {

    List<Meeting> getAllMeetingsWithOwner(EmailAddress email);

    List<Meeting> getAllMeetingsWithParticipant(EmailAddress email);

}