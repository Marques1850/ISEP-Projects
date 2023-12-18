package ecourse.base.MeetingManagement.InvitationManagement.application;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.MeetingManagement.InvitationManagement.repositories.MeetingInviteRepository;
import ecourse.base.MeetingManagement.event.InviteMeetingEvent;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.StudentBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InvitationToMeetingServiceTest {

    private static  InvitationToMeetingService invitationToMeetingService;
    private static eCourseSystemUser emailRecipient;
    private static eCourseSystemUser emailSender;

    @BeforeAll
    static void setUp(){
        SystemUserBuilder systemB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        StudentBuilder sB = new StudentBuilder();
        systemB.withName("sender", "Doe");
        systemB.withUsername("sendertest");
        systemB.withEmail("sendertest@example.com");
        systemB.withPassword("Password22");
        systemB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser = systemB.build();
        sB.withSystemUser(systemUser);
        sB.withEmail("sendertest@example.com");
        sB.withMecanographicNumber("121170000");
        sB.withNIF("100000001");
        sB.withBirthDate("01/01/1990");
        emailSender= sB.build();
        systemB.withName("asdasdasd", "Doe");
        systemB.withUsername("receptest1");
        systemB.withEmail("receptest1@example.com");
        systemB.withPassword("Password22");
        systemB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser2 = systemB.build();
        sB.withSystemUser(systemUser2);
        sB.withEmail("receptest1@example.com");
        sB.withMecanographicNumber("121170000");
        sB.withNIF("100000001");
        sB.withBirthDate("01/01/1990");
        emailRecipient = sB.build();

        MeetingInviteRepository inviteRepo = mock(MeetingInviteRepository.class);
        invitationToMeetingService = new InvitationToMeetingService( inviteRepo);
    }
    @Test
    void testSaveInvitationWithNull() {
        InviteMeetingEvent event = new InviteMeetingEvent(null, null, 0, null, null,0);
        assertThrows(NullPointerException.class, () -> invitationToMeetingService.saveInvitation(event, emailRecipient.eCourseUserEmail()));
    }

    @Test
    void testSaveInvitation() {
        InviteMeetingEvent event = new InviteMeetingEvent(LocalDate.now(),LocalTime.MAX,
                30,
                emailRecipient,
                emailSender,
                120);
        assertTrue(invitationToMeetingService.saveInvitation(event, emailRecipient.eCourseUserEmail()));
    }

}