package ecourse.base.ExamMagnament;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.ExamMagnament.services.ListExamGradesCourseService;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SeeTeacherCourseGradesServiceTest {

    private final ListExamGradesCourseService listExamGradesCourseService = new ListExamGradesCourseService();
    private CourseRepository courseRepository;
    private ExamRepository examRepository;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        examRepository = mock(ExamRepository.class);

    }

    @Test
    public void searchUser() throws ParseException {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("Katy", "Doe");
        sB.withUsername("katydoe");
        sB.withEmail("isep200@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("Teacher"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser, Acronym.valueOf("KDD"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));

        Course course = new Course(CourseCode.valueOf("ISEPP"), "Isep", user, 10, 100, "Isep 2023");
        when(courseRepository.isRegent(user)).thenReturn(course);
        Course result = courseRepository.isRegent(user);


        Date opendate=new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2024");
        Date closedate=new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2024");

        Exam exam = new Exam("EAPLE","Eapli","First exam of EAPLI",course.code(),opendate,closedate);


        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("isep123@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser2 = sB.build();

        eCourseSystemUser user2 = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser2, MecanographicNumber.valueOf("123456789"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));


        ExamGrade examGrade = new ExamGrade(user2, 10);
        exam.addExamGrade(examGrade);

        when(examRepository.findAllOfCourse(course.code())).thenReturn(List.of(exam));

        Assertions.assertEquals(course, result);
        List<ExamGrade> actual = listExamGradesCourseService.findExamGrades(course, examRepository);
        List<ExamGrade> expected = exam.getGrades();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    public void findUser() {
    }
}