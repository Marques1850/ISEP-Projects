package ecourse.base.persistence.impl.jpa;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.Application;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class JpaExamRepositoryTest {

    private JpaExamRepository jpaExamRepository;

     @BeforeEach
     void setup(){
            jpaExamRepository = mock(JpaExamRepository.class);
     }
      @Test
      void findallTest(){

      }


    @Test
    public void findAllOfCourse() throws ParseException {
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
        tB.withAcronym("JFM");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user  = tB.build();
        Course course = new Course(CourseCode.valueOf("EAPLI"), "Math", user, 10, 200, "Mathematics");

        Date opendate=new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2024");
        Date closedate=new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2024");

        Exam exam = new Exam("EAPLE","Eapli","First exam of EAPLI",course.code(),opendate,closedate);

        List<Exam> exams = new ArrayList<>();
        exams.add(exam);

        when(jpaExamRepository.findAllOfCourse(course.code())).thenReturn(exams);
        List<Exam> result = jpaExamRepository.findAllOfCourse(course.code());

        Assertions.assertEquals(exams,result);

    }
}