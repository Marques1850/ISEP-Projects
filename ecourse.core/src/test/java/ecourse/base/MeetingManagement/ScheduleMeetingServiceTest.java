package ecourse.base.MeetingManagement;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.*;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import ecourse.base.MeetingManagement.repositories.MeetingRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.ManagerBuilder;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.BaseRoles;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduleMeetingServiceTest {
    private static ScheduleMeetingService service;
    private static eCourseSystemUser owner;

    @BeforeAll
    static void setUp() {
        eCourseUserRepository userRepo = mock(eCourseUserRepository.class);
        MeetingRepository meetingRepo = mock(MeetingRepository.class);
        mock(UserRepository.class);

        service = new ScheduleMeetingService(userRepo, meetingRepo);
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
        owner = mb.build();

        systemUserBuilder.withUsername("test2");
        systemUserBuilder.withPassword("Test12");
        systemUserBuilder.withEmail("test2@gmail.com");
        systemUserBuilder.withName("test", "test");
        systemUserBuilder.withRole(new RoleAssignment(BaseRoles.MANAGER));
        SystemUser systemUser2 = systemUserBuilder.build();
        mb.withSystemUser(systemUser2);
        mb.withEmail("test2@gmail.com");
        mb.withNIF("123456779");
        mb.withBirthDate("01/01/2000");

        eCourseSystemUser usr2 = mb.build();
        Optional<eCourseSystemUser> optUsr = Optional.of(usr2);
        Optional<eCourseSystemUser> optUsr2 = Optional.empty();
        when(userRepo.searchUser(EmailAddress.valueOf("test2@gmail.com"))).thenReturn(optUsr);
        when(userRepo.searchUser(EmailAddress.valueOf("fail@gmail.com"))).thenReturn(optUsr2);
    }

    @Test
    void testScheduleMeetingWithNoParticipants(){
        LocalTime time = LocalTime.MAX;
        LocalDate date = LocalDate.now();
        int duration = 60;

        assertNull(service.scheduleMeeting(date,time,duration, null,owner));
    }

    @Test
    void testScheduleMeetingWithInvalidParticipants(){
        LocalTime time = LocalTime.MAX;
        LocalDate date = LocalDate.now();
        int duration = 60;
        EmailAddress user1 = EmailAddress.valueOf("fail@gmail.com");
        List<EmailAddress> emails = new ArrayList<>();
        emails.add(user1);
        List<eCourseSystemUser> participants = service.validateParticipants(emails);
        assertTrue(participants.isEmpty());
        assertNull(service.scheduleMeeting(date,time,duration,participants,owner));
    }

    @Test
    void testScheduleMeetingWithInvalidDate() {
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now().minusDays(1);
        LocalDate date2 = LocalDate.now().minusMonths(1);
        LocalDate date3 = LocalDate.now().minusYears(1);
        int duration = 60;
        EmailAddress user1 = EmailAddress.valueOf("test2@gmail.com");
        List<EmailAddress> emails = new ArrayList<>();
        emails.add(user1);
        List<eCourseSystemUser> participants = service.validateParticipants(emails);

        assertNull(service.scheduleMeeting(date,time,duration,participants,owner));
        assertNull(service.scheduleMeeting(date2,time,duration,participants,owner));
        assertNull(service.scheduleMeeting(date3,time,duration,participants,owner));
    }

    @Test
    void testScheduleMeetingWithInvalidTime() {
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        int duration = 60;
        EmailAddress user1 = EmailAddress.valueOf("test2@gmail.com");
        List<EmailAddress> emails = new ArrayList<>();
        emails.add(user1);
        List<eCourseSystemUser> participants = service.validateParticipants(emails);

        assertNull(service.scheduleMeeting(date,time,duration,participants,owner));
    }

    @Test
    void testScheduleMeetingWithInvalidDuration() {
        LocalTime time = LocalTime.MAX;
        LocalDate date = LocalDate.now();
        int duration = -60;
        EmailAddress user1 = EmailAddress.valueOf("test2@gmail.com");
        List<EmailAddress> emails = new ArrayList<>();
        emails.add(user1);
        List<eCourseSystemUser> participants = service.validateParticipants(emails);

        assertNull(service.scheduleMeeting(date,time,duration,participants,owner));
    }

    @Test
    void testScheduleMeetingWithValidParameters() {
        LocalTime time = LocalTime.MAX;
        LocalDate date = LocalDate.now();
        int duration = 60;
        EmailAddress user1 = EmailAddress.valueOf("test2@gmail.com");
        List<EmailAddress> emails = new ArrayList<>();
        emails.add(user1);
        List<eCourseSystemUser> participants = service.validateParticipants(emails);

        assertNotNull(service.scheduleMeeting(date,time,duration,participants,owner));
    }
}