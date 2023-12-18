package ecourse.base.persistence.impl.jpa;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.eCourseSystemUser.UserStatus;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.eCourseSystemUser.eCourseSystemUserMapper;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
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


public class JpaeCourseUserRepositoryTest {
    private JpaeCourseUserRepository jpaeCourseUserRepository;

    @BeforeEach
    void setUp() {
        jpaeCourseUserRepository = mock(JpaeCourseUserRepository.class);
    }

    @Test
    void searchUser_ReturnsUser() {

        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("isep123@gmail.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("MANAGER"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser, NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));
        when(jpaeCourseUserRepository.searchUser(any())).thenReturn(Optional.of(user));
        Optional<eCourseSystemUser> result = jpaeCourseUserRepository.searchUser(EmailAddress.valueOf("isep123@gmail.com") );
        Assertions.assertEquals(user, result.get());
    }

    @Test
    void searchUser_ReturnsNull() {
        when(jpaeCourseUserRepository.searchUser(EmailAddress.valueOf("isep123@gmail.com") )).thenReturn(Optional.empty());

        Optional<eCourseSystemUser> result = jpaeCourseUserRepository.searchUser(EmailAddress.valueOf("isep123@gmail.com") );

        Assertions.assertFalse(result.isPresent());
    }


    @Test
    void alterUserStatus(){
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("isep123@gmail.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("MANAGER"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser, NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));

        eCourseSystemUserDto userDto = eCourseSystemUserMapper.toDto(user);
        when(jpaeCourseUserRepository.searchUser(EmailAddress.valueOf("isep123@gmail.com"))).thenReturn(Optional.of(user));
        when(jpaeCourseUserRepository.alterUserStatus(userDto)).thenCallRealMethod();

        eCourseSystemUser result = jpaeCourseUserRepository.alterUserStatus(userDto);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.eCourseUserStatus().equals(UserStatus.DISABLE));
    }

    @Test
    void totalNumberOfUsers() {
        when(jpaeCourseUserRepository.totalNumberOfUsers()).thenReturn(2);

        int result = jpaeCourseUserRepository.totalNumberOfUsers();

        Assertions.assertEquals(2, result);
    }

    @Test
    void listSystemUsers() {

        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("isep123@gmail.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("MANAGER"));
        SystemUser systemUser1 = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser1, NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));


        sB.withName("Katy", "Doe");
        sB.withUsername("katydoe");
        sB.withEmail("isep200@gmail.com");
        sB.withPassword("Password23");
        sB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser2 = sB.build();

        eCourseSystemUser user2 = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser2, Acronym.valueOf("KPT"), NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));

        List<eCourseSystemUser> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        when(jpaeCourseUserRepository.listSystemUsers()).thenReturn(users);

        List<eCourseSystemUser> result = jpaeCourseUserRepository.listSystemUsers();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }



}