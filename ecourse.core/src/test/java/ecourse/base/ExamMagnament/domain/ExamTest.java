package ecourse.base.ExamMagnament.domain;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {

    @Test
    void gradeBystudentEmpty() {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("Katy", "Doe");
        sB.withUsername("katydoe");
        sB.withEmail("isep200@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("Teacher"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser,
                Acronym.valueOf("MOG"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));
        Exam exam = new Exam("1","Math1","Math First Semester", CourseCode.valueOf("MATHS1"),
                DateUtils.addDays(new Date(),-1),DateUtils.addDays(new Date(),-1));

        Assertions.assertEquals(Optional.empty(),exam.gradeBystudent(user));

    }

    @Test
    void gradeBystudentCorrect() {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("Katy", "Doe");
        sB.withUsername("katydoe");
        sB.withEmail("isep200@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("Teacher"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser,
                Acronym.valueOf("MUG"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));
        Exam exam = new Exam("1","Math1","Math First Semester", CourseCode.valueOf("MATHS1"),
                DateUtils.addDays(new Date(),-1),DateUtils.addDays(new Date(),-1));
        exam.addExamGrade(new ExamGrade(user, 10.0F));
        List<ExamGrade> gradeList = new ArrayList<>();
        gradeList.add(new ExamGrade(user, 10.0F));
        Assertions.assertEquals(gradeList,exam.gradeBystudent(user).get());

    }
}