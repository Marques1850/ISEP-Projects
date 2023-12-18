package ecourse.base.persistence.impl.jpa;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.domain.BoardUserPermissions;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JpaBoardRepositoryTest {

    private JpaBoardRepository jpaBoardRepository;

    @BeforeEach
    void setUp() {
        jpaBoardRepository = mock(JpaBoardRepository.class);
    }

    @Test
    public void findByCode() {

        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("isep123@gmail.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("MANAGER"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser, NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));


        Board board = new Board("ShareBoad_1", 20, 10, user);
        board.setIdForTests(1L);
        Long id = board.getId();
        when(jpaBoardRepository.findByCode(id)).thenReturn(Optional.of(board));
        Optional<Board> result = jpaBoardRepository.findByCode(id);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(board, result.get());
    }

    @Test
    public void getallOwned() {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("isep123@gmail.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("MANAGER"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser, NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));


        Board board = new Board("ShareBoad_1", 20, 10, user);
        board.setIdForTests(1L);
        when(jpaBoardRepository.getallOwned(user)).thenReturn(List.of(board));
        List<Board> result = jpaBoardRepository.getallOwned(user);

        Assertions.assertEquals(board, result.get(0));
    }

    @Test
    public void findBoeardsHiShared() {

        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("isep123@gmail.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("MANAGER"));
        SystemUser systemUser = sB.build();

        eCourseSystemUser user = new eCourseSystemUser(EmailAddress.valueOf("isep123@gmail.com"), systemUser, NIF.valueOf("123456786"), BirthDate.valueOf("01/01/1990"));


        Board board = new Board("ShareBoad_1", 20, 10, user);
        board.setIdForTests(1L);

        sB.withName("Jo", "Cringe");
        sB.withUsername("jocringe");
        sB.withEmail("isep203@gmail.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("MANAGER"));
        SystemUser systemUser2 = sB.build();

        eCourseSystemUser user2 = new eCourseSystemUser(EmailAddress.valueOf("isep203@gmail.com"), systemUser2, NIF.valueOf("132456785"), BirthDate.valueOf("01/09/1995"));
        board.shareBoard(user2, BoardUserPermissions.WRITE);

        when(jpaBoardRepository.findBoeardsHiShared(user2)).thenReturn(List.of(board));
        List<Board> result = jpaBoardRepository.findBoeardsHiShared(user2);

        Assertions.assertEquals(board, result.get(0));
    }
}