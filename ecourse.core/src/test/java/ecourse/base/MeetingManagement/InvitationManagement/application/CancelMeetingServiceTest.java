package ecourse.base.MeetingManagement.InvitationManagement.application;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.RoleAssignment;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.MeetingManagement.InvitationManagement.domain.MeetingInvite;
import ecourse.base.MeetingManagement.InvitationManagement.repositories.MeetingInviteRepository;
import ecourse.base.MeetingManagement.Meeting;
import ecourse.base.MeetingManagement.repositories.MeetingRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.ManagerBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.BaseRoles;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CancelMeetingServiceTest {
    private static CancelMeetingService cancelMeetingService;
    private static Meeting meeting;

    @BeforeAll
    static void setUp() {
        MeetingRepository meetingRepo = mock(MeetingRepository.class);
        MeetingInviteRepository meetingInviteRepo = mock(MeetingInviteRepository.class);
        cancelMeetingService = new CancelMeetingService(meetingRepo, meetingInviteRepo);

        ManagerBuilder mb = new ManagerBuilder();
        SystemUserBuilder systemUserBuilder = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        systemUserBuilder.withUsername("test");
        systemUserBuilder.withPassword("Test12");
        systemUserBuilder.withEmail("test@gmail.com");
        systemUserBuilder.withName("test", "test");
        systemUserBuilder.withRole(new RoleAssignment(BaseRoles.MANAGER));
        SystemUser systemUser = systemUserBuilder.build();

        mb.withSystemUser(systemUser);
        mb.withEmail("test@gmail.com");
        mb.withNIF("123456779");
        mb.withBirthDate("01/01/2000");
        eCourseSystemUser usr = mb.build();

        mb.withSystemUser(systemUser);
        mb.withEmail("test2@gmail.com");
        mb.withNIF("123456779");
        mb.withBirthDate("01/01/2000");
        eCourseSystemUser usr2 = mb.build();

        List<eCourseSystemUser> list = List.of(usr, usr2);

        mb.withSystemUser(systemUser);
        mb.withEmail("owner2@gmail.com");
        mb.withNIF("123456779");
        mb.withBirthDate("01/01/2000");
        eCourseSystemUser owner = mb.build();

        meeting = new Meeting(LocalDate.now(),LocalTime.MAX,30,list,owner);

        MeetingInvite meetingInvite = MeetingInvite.valueOf(LocalDate.now(),meeting.meetingTime(),
                meeting.meetingDuration(),
                meeting.meetingParticipants().get(0).identity(),
                meeting.meetingOwner().identity(),
                1);
        MeetingInvite meetingInvite2 = MeetingInvite.valueOf(LocalDate.now(),meeting.meetingTime(),
                meeting.meetingDuration(),
                meeting.meetingParticipants().get(1).identity(),
                meeting.meetingOwner().identity(),
                1);

        when(meetingRepo.save(meeting)).thenReturn(meeting);
        when(meetingRepo.ofIdentity(meeting.identity())).thenReturn(Optional.of(meeting) );
        when(meetingInviteRepo.searchByMeetingId( meeting.identity())).thenReturn(List.of( meetingInvite, meetingInvite2));
    }

    @Test
    void testCancelMeetingWithAValidMeeting() {
        assertTrue(cancelMeetingService.cancelMeeting(meeting));
    }

}