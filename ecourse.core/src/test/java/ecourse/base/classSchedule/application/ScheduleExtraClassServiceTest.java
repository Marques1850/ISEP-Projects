package ecourse.base.classSchedule.application;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.classSchedule.domain.ExtraClass;
import ecourse.base.classSchedule.repositories.ExtraClassRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.StudentBuilder;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduleExtraClassServiceTest {

    private ExtraClassRepository repo;
    private List<eCourseSystemUser> studentList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        repo = mock(ExtraClassRepository.class);

        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        TeacherBuilder tB = new TeacherBuilder();
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("johndoe@example.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("MANAGER"));
        SystemUser systemUser = sB.build();

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
        studentList.add(user);

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
        studentList.add(user2);
    }

    @Test
    void createExtraClassScheduleNotNull() {

        ExtraClass extraClass = new ExtraClass("Teste1", 90, LocalDateTime.now(), studentList);
        assertNotNull(extraClass);

    }

    @Test
    void createClassScheduleTest(){

        ExtraClass classSchedule1 = new ExtraClass("ClassTest1", 90, LocalDateTime.now(),studentList);


        ExtraClass classSchedule2 = new ExtraClass("ClassTest2", 90, LocalDateTime.now().plusHours(5), studentList);


        ExtraClass classSchedule3 = new ExtraClass("ClassTest3", 90, LocalDateTime.now().plusHours(10), studentList);



        when(repo.getAll()).thenReturn(List.of(classSchedule1, classSchedule2, classSchedule3));
        List<ExtraClass> list = repo.getAll();

        assertTrue(list.contains(classSchedule1));
        assertTrue(list.contains(classSchedule2));
        assertTrue(list.contains(classSchedule3));

    }

}