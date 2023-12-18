package ecourse.base.persistence.impl.jpa;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.Course.CourseDto;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JpaCourseRepositoryTest {

    private JpaCourseRepository jpaCourseRepository;


    @BeforeEach
    void setUp() {
        jpaCourseRepository = mock(JpaCourseRepository.class);
    }

    @Test
    void testFindByCode() {
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
        tB.withAcronym("JDM");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user  = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");
        when(jpaCourseRepository.findByCode(any())).thenReturn(Optional.of(course));

        Optional<Course> result = jpaCourseRepository.findByCode(CourseCode.valueOf("EAPLI"));

        Assertions.assertEquals(course, result.get());
    }

    @Test
    void testFindByCodeReturnsEmptyOptionalIfCourseNotFound() {
        when(jpaCourseRepository.findByCode(CourseCode.valueOf("EAPLI2"))).thenReturn(Optional.empty());

        Optional<Course> result = jpaCourseRepository.findByCode(CourseCode.valueOf("EAPLI2"));

        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    void findAll() {
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
        tB.withAcronym("JDF");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course course1 = new Course(CourseCode.valueOf("EAPLA"), "Math", user, 10, 200, "Mathematics");


        sB.withName("Katy", "Doe");
        sB.withUsername("katydoe");
        sB.withEmail("isep200@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser2 = sB.build();

        eCourseSystemUser user2 = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser2, Acronym.valueOf("KTT"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));

        Course course2 = new Course(CourseCode.valueOf("EPALO"), "Math", user2, 10, 200, "Mathematics");

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        System.out.println(courses.size());
        when(jpaCourseRepository.findAllCourses()).thenReturn(courses);

        List<Course> result = jpaCourseRepository.findAllCourses();
        System.out.println(result.size());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void testFindAllOpen() {
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
        tB.withAcronym("JDY");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course openCourse = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");


        sB.withName("Katy", "Doe");
        sB.withUsername("katydoe");
        sB.withEmail("isep200@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser2 = sB.build();

        eCourseSystemUser user2 = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser2, Acronym.valueOf("KTP"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));

        Course closedCourse = new Course(CourseCode.valueOf("EAPLO"), "Math", user2, 10, 200, "Mathematics");


        openCourse.openCourse();
        closedCourse.closeCourse();

        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(openCourse);

        when(jpaCourseRepository.findAllOpen()).thenReturn(expectedCourses);

        List<Course> result = jpaCourseRepository.findAllOpen();

        Assertions.assertEquals(expectedCourses, result);
    }

    @Test
    void testFindAllClosed() {
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
        tB.withAcronym("JDW");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course openCourse = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");


        sB.withName("Katy", "Doe");
        sB.withUsername("katydoe");
        sB.withEmail("isep200@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser2 = sB.build();

        eCourseSystemUser user2 = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser2, Acronym.valueOf("KTR"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));

        Course closedCourse = new Course(CourseCode.valueOf("EAPLO"), "Math", user2, 10, 200, "Mathematics");


        openCourse.openCourse();
        closedCourse.closeCourse();

        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(closedCourse);

        when(jpaCourseRepository.findAllClosed()).thenReturn(expectedCourses);

        List<Course> result = jpaCourseRepository.findAllClosed();

        Assertions.assertEquals(expectedCourses, result);
    }

    @Test
    void testVerifyCourseReturnsTrueIfCourseExists() {
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
        tB.withAcronym("JDJ");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user  = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");

        when(jpaCourseRepository.verifyCourse("EAPLI")).thenReturn(true);

        boolean result = jpaCourseRepository.verifyCourse("EAPLI");

        Assertions.assertTrue(result);
    }

    @Test
    void testVerifyCourseReturnsFalseIfCourseDoesNotExist() {
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
        tB.withAcronym("JDK");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");

        when(jpaCourseRepository.verifyCourse("EAPLI2")).thenReturn(false);

        boolean result = jpaCourseRepository.verifyCourse("EAPLI2");

        Assertions.assertFalse(result);
    }

    @Test
    void testVerifyCourseIsActiveReturnsFalse(){
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
        tB.withAcronym("JDD");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");

        when(jpaCourseRepository.findByCode(CourseCode.valueOf("EAPLI"))).thenReturn(Optional.of(course));
        when(jpaCourseRepository.VerifyCourseIsActive("EAPLI")).thenCallRealMethod();

        boolean result;

        if(jpaCourseRepository.VerifyCourseIsActive("EAPLI") == null){
            result = true;
        } else {
            result = false;
        }

        Assertions.assertTrue(result);


    }


    @Test
    void testVerifyCourseIsActiveReturnsTrue() {
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
        tB.withAcronym("JDL");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");

        course.openCourse();

        when(jpaCourseRepository.findByCode(CourseCode.valueOf("EAPLI"))).thenReturn(Optional.of(course));
        when(jpaCourseRepository.VerifyCourseIsActive("EAPLI")).thenCallRealMethod();

        CourseDto dto = jpaCourseRepository.VerifyCourseIsActive("EAPLI");

        boolean result;

        if(dto == null){
            result = false;
        } else {
            result = true;
        }

        Assertions.assertTrue(result);

    }

    @Test
    void testVerifyCourseIsActiveAndEnrollment() {
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
        tB.withAcronym("JDC");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");

        course.openCourse();
        course.enableEnrollments();

        when(jpaCourseRepository.findByCode(CourseCode.valueOf("EAPLI"))).thenReturn(Optional.of(course));
        when(jpaCourseRepository.VerifyCourseIsActiveAndEnrollment("EAPLI")).thenCallRealMethod();

        CourseDto dto = jpaCourseRepository.VerifyCourseIsActiveAndEnrollment("EAPLI");

        boolean result;

        if(dto == null){
            result = false;
        } else {
            result = true;
        }

        Assertions.assertTrue(result);

    }

    @Test
    void testVerifyCourseIsActiveButEnrollment() {
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
        tB.withAcronym("JDV");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");

        course.openCourse();

        when(jpaCourseRepository.findByCode(CourseCode.valueOf("EAPLI"))).thenReturn(Optional.of(course));
        when(jpaCourseRepository.VerifyCourseIsActiveAndEnrollment("EAPLI")).thenCallRealMethod();

        CourseDto dto = jpaCourseRepository.VerifyCourseIsActiveAndEnrollment("EAPLI");

        boolean result;

        if(dto == null){
            result = true;
        } else {
            result = false;
        }

        Assertions.assertTrue(result);
    }

    @Test
    void enableenrollments(){
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
        tB.withAcronym("JDB");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");

        course.openCourse();

        when(jpaCourseRepository.findByCode(CourseCode.valueOf("EAPLI"))).thenReturn(Optional.of(course));
        when(jpaCourseRepository.enableenrollments("EAPLI")).thenCallRealMethod();

        CourseDto dto = jpaCourseRepository.enableenrollments("EAPLI");

        boolean result;

        if(dto.getStatus().equals("OPEN_ENROLLMENT")){
            result = true;
        } else {
            result = false;
        }

        Assertions.assertTrue(result);
    }

    @Test
    void disableenrollments(){
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
        tB.withAcronym("JDN");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");

        course.openCourse();
        course.enableEnrollments();

        when(jpaCourseRepository.findByCode(CourseCode.valueOf("EAPLI"))).thenReturn(Optional.of(course));
        when(jpaCourseRepository.disableenrollments("EAPLI")).thenCallRealMethod();

        CourseDto dto = jpaCourseRepository.disableenrollments("EAPLI");

        boolean result;

        if(!(dto.getStatus().equals("OPEN_ENROLLMENT"))){
            result = true;
        } else {
            result = false;
        }

        Assertions.assertTrue(result);
    }



}