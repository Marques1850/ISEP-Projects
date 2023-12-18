package ecourse.base.MeetingManagement.InvitationManagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInviteState;

import java.util.List;

public interface MeetingInviteRepository extends DomainRepository<Integer, MeetingInvite> {

    List<MeetingInvite> searchByMeetingId(int meetingID);

    List<MeetingInvite> getAllMeetingInvitationsWithRecipient(EmailAddress email);

    MeetingInvite updateMeetingInviteState(MeetingInvite meetingInvite, MeetingInviteState inviteState);

}