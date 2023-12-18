package ecourse.base.MeetingManagement.InvitationManagement.application;

import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.InvitationManagement.repositories.MeetingInviteRepository;
import ecourse.base.MeetingManagement.Meeting;
import ecourse.base.MeetingManagement.repositories.MeetingRepository;

import java.util.List;

public class CancelMeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingInviteRepository meetingInviteRepository;

    CancelMeetingService(MeetingRepository meetingRepository, MeetingInviteRepository meetingInviteRepository){
        this.meetingRepository = meetingRepository;
        this.meetingInviteRepository = meetingInviteRepository;
    }

    public boolean cancelMeeting( Meeting meeting ){
        try {
            List<MeetingInvite> invitesToRemove = meetingInviteRepository.searchByMeetingId(meeting.identity());
            for(MeetingInvite invite : invitesToRemove){
                meetingInviteRepository.remove(invite);
            }
            meetingRepository.remove(meeting);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
