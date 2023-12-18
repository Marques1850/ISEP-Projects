package ecourse.base.eCourseSystemUser;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.usermanagement.UserRegistration.StudentBuilder;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class eCourseSystemUserTest {
    private static eCourseSystemUser user1;
    private static eCourseSystemUser user2;
    private static Course test;

    @BeforeAll
    static  void  setUpAll() {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        TeacherBuilder tB = new TeacherBuilder();
        StudentBuilder b = new StudentBuilder();
        sB.withName("John", "Do");
        sB.withUsername("johndo");
        sB.withEmail("johndo@example.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser = sB.build();

        b.withSystemUser(systemUser);
        b.withEmail("johndo@example.com");
        b.withNIF("123456786");
        b.withBirthDate("01/01/1990");

        tB.withSystemUser(systemUser);
        tB.withEmail("johndoe@example.com");
        tB.withAcronym("JHD");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");

        test = new Course( "Test1", CourseCode.valueOf("TEST1"));
        user1 = tB.build();
        user2 = b.build();
    }

    @Test
    void setTeacherCourse() {
        user1.setTeacherCourse(test);

        Assertions.assertTrue(user1.coursesTeaching().contains(test));
    }

    @Test
    void setStudentCourse() {
        user2.setStudentCourse(test);

        Assertions.assertTrue(user2.coursesLearning().contains(test));
    }

    @Test
    void alterUserStatus() {
        user1.alterUserStatus();

        Assertions.assertEquals(user1.eCourseUserStatus(), UserStatus.DISABLE);

        user1.alterUserStatus();

        Assertions.assertEquals(user1.eCourseUserStatus(), UserStatus.ENABLE);
    }
}