package ecourse.base.ExamMagnament.ListFutureExam;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListFutureExamsServiceTest {
    private final ListFutureExamsService listFutureExamsService = new ListFutureExamsService();

    @Test
    void getFutureExams() {
    }

    @Test
    void getStudentCoursesTest() {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("Katy", "Doe");
        sB.withUsername("katydoe");
        sB.withEmail("isep200@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser, MecanographicNumber.valueOf("123456789"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));

        Course course = new Course("ISEP", CourseCode.valueOf("LEI1AAA"));
        Course course1 = new Course("ISEP", CourseCode.valueOf("LEE1AAA"));
        user.setStudentCourse(course);
        user.setStudentCourse(course1);
        List<Course> actual=listFutureExamsService.getStudentCourses(user);
        List<Course> expected=List.of(course,course1);
        assertEquals(expected,actual);

    }
}