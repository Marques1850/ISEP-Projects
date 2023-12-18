package ecourse.base.courseManagement;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.StudentBuilder;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.application.CourseBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

class TeachersinCourseServiceTest {
   public eCourseSystemUser student1;
    public eCourseSystemUser teacher1;
    public eCourseSystemUser teacher2;
    public eCourseSystemUser teacher3;
    public Course math;


    @BeforeEach
    void setUp() {

        CourseBuilder cB = new CourseBuilder();
        cB.withCode("EAPLI");
        cB.withName("Math");
        cB.withMinStudents(10);
        cB.withMaxStudents(200);
        cB.withDescription("Mathematics");

        math = cB.build();

    }
    @Test
    void SetCourseTeachingNonTeacherTest() {
        SystemUserBuilder systemB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        StudentBuilder sB = new StudentBuilder();
        systemB.withName("John", "Doe");
        systemB.withUsername("johndoe");
        systemB.withEmail("johndoe@example.com");
        systemB.withPassword("Password22");
        systemB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser = systemB.build();

        sB.withSystemUser(systemUser);
        sB.withEmail("johndoe@example.com");
        sB.withMecanographicNumber("121170000");
        sB.withNIF("123456786");
        sB.withBirthDate("01/01/1990");


        student1= sB.build();

        List<eCourseSystemUser> teachers=new ArrayList<>();
        teachers.add(student1);
      teachers=TeachersinCourseService.setTeachersCourse(math,teachers);
        Assertions.assertTrue(teachers.isEmpty());

    }
    @Test
    void SetCourseTeachingAlreadySetCourseTest() {

        SystemUserBuilder systemB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        TeacherBuilder tB = new TeacherBuilder();
        systemB.withName("John", "Doee");
        systemB.withUsername("johndoee");
        systemB.withEmail("johndoee@example.com");
        systemB.withPassword("Password22");
        systemB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser2 = systemB.build();

        tB.withSystemUser(systemUser2);
        tB.withEmail("johndoee@example.com");
        tB.withAcronym("JDE");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");
        teacher1 = tB.build();

        List<eCourseSystemUser> teachers=new ArrayList<>();
        teachers.add(teacher1);
        teacher1.setTeacherCourse(math);
        teachers=TeachersinCourseService.setTeachersCourse(math,teachers);
        Assertions.assertTrue(teachers.isEmpty());


    }
    @Test
    void SetCourseTeachingTest() {
        SystemUserBuilder systemB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        TeacherBuilder tB = new TeacherBuilder();
        systemB.withName("John", "Straw");
        systemB.withUsername("Palhinha");
        systemB.withEmail("johnStraw@example.com");
        systemB.withPassword("Password22");
        systemB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser3 = systemB.build();

        tB.withSystemUser(systemUser3);
        tB.withEmail("johnStraw@example.com");
        tB.withAcronym("JST");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");
        teacher2 = tB.build();

        systemB.withName("Kevin", "Straw");
        systemB.withUsername("Kalhinha");
        systemB.withEmail("KevinStraw@example.com");
        systemB.withPassword("Password22");
        systemB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser4 = systemB.build();

        tB.withSystemUser(systemUser4);
        tB.withEmail("KevinStraw@example.com");
        tB.withAcronym("KST");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");
        teacher3 = tB.build();


        List<eCourseSystemUser> teachers=new ArrayList<>();
        teachers.add(teacher2);
        teachers.add(teacher3);
        Assertions.assertAll( () -> {
            TeachersinCourseService.setTeachersCourse(math,teachers);
        });

    }

}