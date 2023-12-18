package ecourse.base.usermanagement.application;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourseBuilderTest {

    @Test
    public void buildCourse() {

        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        TeacherBuilder tB = new TeacherBuilder();
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("johndoe@example.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser = sB.build();

        tB.withSystemUser(systemUser);
        tB.withEmail("johndoe@example.com");
        tB.withAcronym("JID");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user  = tB.build();

        CourseBuilder cB = new CourseBuilder();
        cB.withCode("EAPLI");
        cB.withName("Math");
        cB.withRegent(user);
        cB.withMinStudents(10);
        cB.withMaxStudents(200);
        cB.withDescription("Mathematics");

        Course course = cB.build();

        assertNotNull(course);
        assertEquals(course.code().courseCode(), "EAPLI");
        assertEquals(course.name(), "Math");
        assertEquals(course.regent(), user);
        assertEquals(course.minStudents(), 10);
        assertEquals(course.maxStudents(), 200);
        assertEquals(course.description(), "Mathematics");

    }

}