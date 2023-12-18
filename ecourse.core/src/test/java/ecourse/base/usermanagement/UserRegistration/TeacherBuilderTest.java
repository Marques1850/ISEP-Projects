package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.*;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeacherBuilderTest {

    @Test
    public void buildTeacher() {
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
        tB.withAcronym("JNB");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");


        eCourseSystemUser user  = tB.build();

        assertNotNull(user);
        assertEquals(user.systemUser().name().toString(), Name.valueOf("John", "Doe").toString());
        assertEquals(user.systemUser().username().toString(), Username.valueOf("johndoe").toString());
        assertEquals(user.eCourseUserEmail().toString(), EmailAddress.valueOf("johndoe@example.com").toString());
        //assertEquals(user.systemUser()., Password.encodedAndValid("Password22", new BasePasswordPolicy(), new PlainTextEncoder()).get());
        assertEquals(user.teacherAcronym().acronym(), "JNB");
        assertEquals(user.eCourseUserNIF().nif(), NIF.valueOf("123456786").nif());
        assertEquals(user.eCourseUserBirthDate().birthDate(), BirthDate.valueOf("01/01/1990").birthDate());
    }

}