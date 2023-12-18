package ecourse.base.MeetingManagement;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.StudentBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeetingTest {
    private static eCourseSystemUser emailSender;
    private static final List<eCourseSystemUser> participants = new ArrayList<>();

    @BeforeAll
    static void setUp() {

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
        eCourseSystemUser emailRecipient = sB.build();
        systemB.withName("asdasda", "Doe");
        systemB.withUsername("receptest2");
        systemB.withEmail("receptest2@example.com");
        systemB.withPassword("Password22");
        systemB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser3 = systemB.build();
        sB.withSystemUser(systemUser3);
        sB.withEmail("receptest2@example.com");
        sB.withMecanographicNumber("121170000");
        sB.withNIF("100000001");
        sB.withBirthDate("01/01/1990");
        eCourseSystemUser emailRecipient2 = sB.build();

        participants.add(emailRecipient);
        participants.add(emailRecipient2);

    }
    @Test
    void testMeetingAddParticipants() {
        Meeting meeting = new Meeting(LocalDate.now(),LocalTime.MAX, 60, emailSender);
        meeting.addParticipants(participants);

        assertEquals(2, meeting.meetingParticipants().size());
        assertArrayEquals(participants.toArray(), meeting.meetingParticipants().toArray());
    }

    @Test
    void testMeetingDurationMethod() {
        Meeting meeting = new Meeting(LocalDate.now(),LocalTime.MAX, 60, emailSender);
        assertEquals(60, meeting.meetingDuration());
    }

    @Test
    void testMeetingTimeMethod() {
        Meeting meeting = new Meeting(LocalDate.now(),LocalTime.MAX, 60, emailSender);
        assertEquals(LocalTime.MAX, meeting.meetingTime());
    }

    @Test
    void testMeetingOwnerMethod() {
        Meeting meeting = new Meeting(LocalDate.now(),LocalTime.MAX, 60, emailSender);
        assertEquals(emailSender, meeting.meetingOwner());
    }

    @Test
    void testMeetingParticipantsMethod() {
        Meeting meeting = new Meeting(LocalDate.now(),LocalTime.MAX, 60, participants, emailSender);

        assertEquals(2, meeting.meetingParticipants().size());
        assertArrayEquals(participants.toArray(), meeting.meetingParticipants().toArray());
    }

    @Test
    void testMeetingEquals() {
        Meeting meeting = new Meeting(LocalDate.now(),LocalTime.MAX, 60, emailSender);
        Meeting meeting2 = new Meeting(LocalDate.now(),LocalTime.MAX, 60, emailSender);
        Meeting meeting3 = new Meeting(LocalDate.now(),LocalTime.MAX, 60, participants, emailSender);

        assertEquals(meeting, meeting2);
        assertNotEquals(meeting, meeting3);
    }

}