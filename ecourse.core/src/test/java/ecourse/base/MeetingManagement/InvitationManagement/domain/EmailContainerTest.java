package ecourse.base.MeetingManagement.InvitationManagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailContainerTest {
    @Test
    void testEqualsEmailContainer() {
        EmailContainer emailContainer1 = new EmailContainer("recipientEmail", "senderEmail");
        EmailContainer emailContainer2 = new EmailContainer("recipientEmail", "senderEmail");
        EmailContainer emailContainer3 = new EmailContainer("recipientEmail", "senderEmail2");


        assertEquals(emailContainer1, emailContainer2);
        assertNotEquals(emailContainer1, emailContainer3);
    }
}