package ecourse.base.usermanagement.domain.Course;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.*;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.BaseRoles;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    @Test
    public void ensureCourseIsCreated(){

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
        tB.withAcronym("JML");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser regent  = tB.build();


        Course cou = new Course(CourseCode.valueOf("EAPLI"), "Math", regent, 10, 200, "Mathematics");

        assertNotNull(cou);
        assertEquals(cou.code().courseCode(), "EAPLI");
        assertEquals(cou.name(), "Math");
        assertEquals(cou.regent(), regent);
        assertEquals(cou.minStudents(), 10);
        assertEquals(cou.maxStudents(), 200);
        assertEquals(cou.description(), "Mathematics");
        assertEquals(cou.Status(), CourseStatus.CLOSED);

    }

    @Test
    public void ensureCourseIsClosed(){
        Course cou=new Course("name",CourseCode.valueOf("EAPLI"));
        cou.CloseCourse();
        assertEquals(CourseStatus.CLOSED,cou.Status());
    }
    @Test
    public void ensureCourseIsOpen(){
        Course cou=new Course("name",CourseCode.valueOf("EAPLI"));
        cou.CloseCourse();
        cou.OpenCourse();
        assertEquals(CourseStatus.OPEN,cou.Status());
    }


}