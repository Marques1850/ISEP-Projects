package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.*;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentBuilderTest {

    @Test
    public void buildStudent() {
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


        eCourseSystemUser user = sB.build();

        assertNotNull(user);
        assertEquals(user.systemUser().name().toString(), Name.valueOf("John", "Doe").toString());
        assertEquals(user.systemUser().username().toString(), Username.valueOf("johndoe").toString());
        assertEquals(user.eCourseUserEmail().toString(), EmailAddress.valueOf("johndoe@example.com").toString());
        //assertEquals(user.systemUser()., Password.encodedAndValid("Password22", new BasePasswordPolicy(), new PlainTextEncoder()).get());
        assertEquals(user.studentMecanographicNumber().number(), MecanographicNumber.valueOf("121170000").number());
        assertEquals(user.eCourseUserNIF().nif(), NIF.valueOf("123456786").nif());
        assertEquals(user.eCourseUserBirthDate().birthDate(), BirthDate.valueOf("01/01/1990").birthDate());

    }

}