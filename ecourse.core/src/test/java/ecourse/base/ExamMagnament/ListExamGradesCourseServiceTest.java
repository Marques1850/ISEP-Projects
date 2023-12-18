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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListExamGradesCourseServiceTest {

    private final ListExamGradesCourseService listExamGradesCourseService = new ListExamGradesCourseService();

    private ExamRepository examRepository;

    @BeforeEach
    void setUp() {
        examRepository = mock(ExamRepository.class);

    }

    @Test
    public void findExamGrades() throws ParseException {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("Katy", "Doe");
        sB.withUsername("katydoe");
        sB.withEmail("isep200@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("Teacher"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser, Acronym.valueOf("POD"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));

        Course course = new Course(CourseCode.valueOf("ISEPP"), "Isep", user, 10, 100, "Isep 2023");

        Date opendate=new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2024");
        Date closedate=new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2024");

        Exam exam = new Exam("EAPLE","Eapli","First exam of EAPLI",course.code(),opendate,closedate);
        Exam exam2 = new Exam("EAPLE2","Eapli2","Second exam of EAPLI",course.code(),opendate,closedate);
        List<Exam> exams  = new ArrayList<>();
        exams.add(exam);
        exams.add(exam2);

        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("isep123@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser2 = sB.build();

        eCourseSystemUser user2 = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser2, MecanographicNumber.valueOf("123456789"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));


        ExamGrade examGrade = new ExamGrade(user2, 10);
        ExamGrade examGrade2 = new ExamGrade(user2, 20);
        exam.addExamGrade(examGrade);
        exam.addExamGrade(examGrade2);

        when(examRepository.findAllOfCourse(course.code())).thenReturn(exams);
        List<ExamGrade> examGrades = listExamGradesCourseService.findExamGrades(course, examRepository);

        Assertions.assertEquals(2, examGrades.size());
    }
}