package ecourse.base.MeetingManagement.InvitationManagement.domain;

import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class MeetingInviteTest {
    private static EmailAddress emailRecipient;
    private static EmailAddress emailSender;

    @BeforeAll
    static void setUp() {
         emailRecipient = EmailAddress.valueOf("recp@gmail.com");
         emailSender = EmailAddress.valueOf("sender@gmail.com");
    }
    @Test
    void testMeetingInviteValueOf() {
        MeetingInvite test = MeetingInvite.valueOf( LocalDate.now(),LocalTime.MAX, 20, emailRecipient, emailSender, 1);
        assertNotNull(test);
        assertEquals(test.meetingID(), 1);
        assertEquals(test.emailContainer().recipientEmail(), emailRecipient.toString());
    }
    @Test
    void testMeetingInviteValueOfWithNulls() {
        assertThrows(IllegalArgumentException.class, () -> MeetingInvite.valueOf(null, null,1, null, null, 1));
    }

    @Test
    void testAcceptMeetingInviteMethod() {
        MeetingInvite test = MeetingInvite.valueOf(LocalDate.now(),LocalTime.MAX, 20, emailRecipient, emailSender, 1);
        test.acceptInvite();
        assertEquals(test.state(), MeetingInviteState.ACCEPTED);
    }

    @Test
    void testRejectMeetingInviteMethod() {
        MeetingInvite test = MeetingInvite.valueOf(LocalDate.now(),LocalTime.MAX, 20, emailRecipient, emailSender, 1);
        test.rejectInvite();
        assertEquals(test.state(), MeetingInviteState.REJECTED);
    }

    @Test
    void testMeetingInviteObtainMeetingID() {
        MeetingInvite test = MeetingInvite.valueOf(LocalDate.now(),LocalTime.MAX, 20, emailRecipient, emailSender, 1);
        assertEquals(test.meetingID(), 1);
    }

    @Test
    void testMeetingInviteObtainRecipientEmail() {
        MeetingInvite test = MeetingInvite.valueOf(LocalDate.now(),LocalTime.MAX, 20, emailRecipient, emailSender, 1);
        assertEquals(test.emailContainer().recipientEmail(), emailRecipient.toString());
    }

    @Test
    void testMeetingInviteEquals() {
        MeetingInvite test = MeetingInvite.valueOf(LocalDate.now(),LocalTime.MAX, 20, emailRecipient, emailSender, 1);
        MeetingInvite test2 = MeetingInvite.valueOf(LocalDate.now(),LocalTime.MAX, 20, emailRecipient, emailSender, 1);
        MeetingInvite test3 = MeetingInvite.valueOf(LocalDate.now(),LocalTime.MIN, 20, emailRecipient, emailSender, 2);

        assertEquals(test, test2);
        assertNotEquals(test, test3);
    }
}