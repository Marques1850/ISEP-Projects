package ecourse.base.EnrollmentManagment.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentIDTest {

    @Test
    void testEquals() {
        EnrollmentID id1 = new EnrollmentID( "TEST1");
        EnrollmentID id2 = new EnrollmentID( "TEST1");
        EnrollmentID id3 = new EnrollmentID( "TEST2");

        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
    }
}