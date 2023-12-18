package ecourse.base.EnrollmentManagment;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.eCourseSystemUser.eCourseSystemUserMapper;
import ecourse.base.usermanagement.UserRegistration.StudentBuilder;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BulkEnrollServiceTest {
    private eCourseUserRepository repo ;
    @BeforeEach
    void setUp() {
        repo = mock(eCourseUserRepository.class);

    }

    @Test
    void parseCSV() throws FileNotFoundException {
           /* BulkEnrollService bulkEnrollService = new BulkEnrollService();
            String csvPath = "ecourse.core/src/main/java/ecourse/base/EnrollmentManagment/test.csv";


            Set<MecanographicNumber> result = bulkEnrollService.parseCSV(csvPath);
            Assert.assertEquals(9, result.size());

            Assert.assertTrue(result.contains(new MecanographicNumber("123456789")));
            Assert.assertTrue(result.contains(new MecanographicNumber("987654321")));
            Assert.assertTrue(result.contains(new MecanographicNumber("555555555")));
            Assert.assertTrue(result.contains(new MecanographicNumber("912354321")));
*/
    }

    @Test
    void validateStudents() {
            // Create a list of MecanographicNumbers
            BulkEnrollService bulkEnrollService = new BulkEnrollService();

            // Create a mock repository with some existing MecanographicNumber
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
            sB.withMecanographicNumber("123456789");
            sB.withNIF("123456786");
            sB.withBirthDate("01/01/1990");
            eCourseSystemUser user = sB.build();


            List<MecanographicNumber> mecanographicNumbers = new ArrayList<>();
            mecanographicNumbers.add(new MecanographicNumber("123456789"));
            mecanographicNumbers.add(new MecanographicNumber("987654321"));
            mecanographicNumbers.add(new MecanographicNumber("555555555"));
            mecanographicNumbers.add(new MecanographicNumber("912354321"));

            List<MecanographicNumber> correctedResults = new ArrayList<>();
            when(repo.findByMeca(new MecanographicNumber("123456789"))).thenReturn(Optional.of(user));
            correctedResults= bulkEnrollService.validateStudents(mecanographicNumbers, repo);


            // Assert the expected number of remaining MecanographicNumbers
            Assert.assertEquals(1, correctedResults.size());

            // Assert the expected MecanographicNumbers in the list
            Assert.assertTrue(correctedResults.contains(new MecanographicNumber("123456789")));

    }

    @Test
    void enrollStudentsNotFull() throws FileNotFoundException {
/*
        BulkEnrollService bulkEnrollService = new BulkEnrollService();
        //Create a Teacher
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
        tB.withAcronym("JMY");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");
        eCourseSystemUser regent  = tB.build();

        /////Student 1

        SystemUserBuilder systemB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        StudentBuilder sBS = new StudentBuilder();
        systemB.withName("John", "Doe");
        systemB.withUsername("johndoe");
        systemB.withEmail("johndoe@example.com");
        systemB.withPassword("Password22");
        systemB.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser1 = systemB.build();
        sBS.withSystemUser(systemUser);
        sBS.withEmail("johndoe@example.com");
        sBS.withMecanographicNumber("123456789");
        sBS.withNIF("123456786");
        sBS.withBirthDate("01/01/1990");
        eCourseSystemUser user = sBS.build();
        repo.save(user);

        /////Student 2

        SystemUserBuilder systemB2 = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        StudentBuilder sBS2 = new StudentBuilder();
        systemB2.withName("Johnn", "Doee");
        systemB2.withUsername("johndoe2");
        systemB2.withEmail("johndoe2@example.com");
        systemB2.withPassword("Password22");
        systemB2.withRoles(Role.valueOf("STUDENT"));
        SystemUser systemUser2 = systemB2.build();
        sBS2.withSystemUser(systemUser2);
        sBS2.withEmail("johndoe2@example.com");
        sBS2.withMecanographicNumber("123456788");
        sBS2.withNIF("123456786");
        sBS2.withBirthDate("01/01/1990");
        eCourseSystemUser user2 = sBS2.build();
        repo.save(user2);


        // Create a Course
        Course cou = new Course(CourseCode.valueOf("EAPLI"), "Math", regent, 1, 5, "Mathematics");
        cou.numStudents(0);

        // Create a list of MecanographicNumbers
        MecanographicNumber mecanographicNumber1 = new MecanographicNumber("123456789");
        MecanographicNumber mecanographicNumber2 = new MecanographicNumber("123456788");
        Set<MecanographicNumber> mecanographicNumbers = new HashSet<>();
        mecanographicNumbers.add(mecanographicNumber1);
        mecanographicNumbers.add(mecanographicNumber2);


        eCourseSystemUserMapper mapper = new eCourseSystemUserMapper();

        // Call the enrollStudents method

        when(repo.findByMeca(new MecanographicNumber("123456789"))).thenReturn(Optional.of(user));
        when(repo.findByMeca(new MecanographicNumber("123456788"))).thenReturn(Optional.of(user));
        List<eCourseSystemUserDto> result = bulkEnrollService.enrollStudents(cou, "C:\\Uni\\EAPLI\\sem4pi-22-23-41\\ecourse.core\\src\\test\\java\\ecourse\\base\\EnrollmentManagment\\test.csv",repo);

        // Assert the expected number of enrolled students
        Assert.assertEquals(2, result.size());

        // Assert the expected enrolled students
        //Assert.assertEquals("John Doe", result.get(0).getSystemUser());
        //Assert.assertEquals("Johnn Doee", result.get(1).getSystemUser());

        // Assert the expected number of students in the course
        Assert.assertEquals(2, cou.numStudents());
        */

    }
}