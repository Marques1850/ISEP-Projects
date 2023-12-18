package ecourse.base.EnrollmentManagment.domain;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.StudentBuilder;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {
    private static Enrollment Enroll;
    private static eCourseSystemUser regent;

    @BeforeAll
    static void setUpAll() {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        TeacherBuilder tB = new TeacherBuilder();
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("johndoe@example.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("MANAGER"));
        SystemUser systemUser = sB.build();

        tB.withSystemUser(systemUser);
        tB.withEmail("johndoe@example.com");
        tB.withAcronym("JDA");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");
        regent  = tB.build();

        Course cou = new Course(CourseCode.valueOf("EAPLI"), "Math", regent, 1, 5, "Mathematics");

        SystemUserBuilder systemB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        StudentBuilder sB2 = new StudentBuilder();
        systemB.withName("John", "Doe");
        systemB.withUsername("johndoe");
        systemB.withEmail("johndoe@example.com");
        systemB.withPassword("Password22");
        systemB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser2 = systemB.build();
        sB2.withSystemUser(systemUser2);
        sB2.withEmail("johndoe@example.com");
        sB2.withMecanographicNumber("123456789");
        sB2.withNIF("123456786");
        sB2.withBirthDate("01/01/1990");
        eCourseSystemUser user = sB2.build();
        String mecanographic = "123456789";

         Enroll = new Enrollment(cou, mecanographic);
    }


    @Test
    void alterEnrollmentStatus() {
        Enroll.alterEnrollmentStatus(EnrollmentStatus.valueOf("ACCEPTED"));

        assertEquals(EnrollmentStatus.ACCEPTED, Enroll.status());

        Enroll.alterEnrollmentStatus(EnrollmentStatus.valueOf("REJECTED"));

        assertEquals(EnrollmentStatus.REJECTED, Enroll.status());

    }

    @Test
    void sameAs() {
        Course cou2 = new Course(CourseCode.valueOf("FALIMA"), "Math", regent, 1, 5, "FLAMA");

        Enrollment Enroll2 = Enroll;

        assertTrue(Enroll2.sameAs(Enroll));

        Enrollment Enroll3 = new Enrollment(cou2, "123456788");

        assertFalse(Enroll.sameAs(Enroll3));
    }
}