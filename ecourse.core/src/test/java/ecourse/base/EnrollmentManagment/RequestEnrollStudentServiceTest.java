package ecourse.base.EnrollmentManagment;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.EnrollmentManagment.domain.Enrollment;
import ecourse.base.EnrollmentManagment.repositories.EnrollmentRepository;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.StudentBuilder;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestEnrollStudentServiceTest {
    private EnrollmentRepository repo ;
    @BeforeEach
    void setUp() {
        repo = mock(EnrollmentRepository.class);

    }

    @Test
    void requestEnrollStudent() {
        RequestEnrollStudentService service = new RequestEnrollStudentService();
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
        tB.withAcronym("JDD");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");
        eCourseSystemUser regent  = tB.build();
        // Create a mock Course object
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
        // Create a mock MecanographicNumber
        String mecanographic = "123456789";

        // Create a mock Enrollment object
        Enrollment Enroll = new Enrollment(cou, mecanographic);
        // Call the requestEnrollStudent method
        when(repo.findAllStudentEnrollments(user)).thenReturn(List.of(Enroll));
        boolean result = service.requestEnrollStudent(cou, mecanographic, repo);

        // Assert that the enrollment request was successful
        assertTrue(result);

        // Assert that the enrollment was saved in the repository
        List<Enrollment> enrollments = repo.findAllStudentEnrollments(user);
        assertNotNull(enrollments);
        assertEquals(enrollments.get(0).studentID().number(), mecanographic);
    }
}