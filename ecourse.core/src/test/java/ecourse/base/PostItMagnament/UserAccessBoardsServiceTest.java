package ecourse.base.PostItMagnament;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.domain.BoardUserPermissions;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserAccessBoardsServiceTest {


    private BoardRepository BoardRepository;
    private eCourseUserRepository eCourseUserRepository;

    @BeforeEach
    void setUp() {
        BoardRepository = mock(BoardRepository.class);
        eCourseUserRepository = mock(eCourseUserRepository.class);
    }
    @Test
    public void findBoardsOfUser() {
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

        Board board2 = new Board("ShareBoad_2", 20, 10, user2);
        board2.shareBoard(user, BoardUserPermissions.WRITE);

        List<Board> ownBoards = new ArrayList<>();
        ownBoards.add(board);

        List<Board> sharedBoards = new ArrayList<>();
        sharedBoards.add(board2);

        when(eCourseUserRepository.searchUser(EmailAddress.valueOf("isep123@gmail.com"))).thenReturn(Optional.of(user));
        when(BoardRepository.getallOwned(user)).thenReturn(ownBoards);
        when(BoardRepository.findBoeardsHiShared(user)).thenReturn(sharedBoards);

        if(!(sharedBoards.isEmpty())){
            ownBoards.addAll(sharedBoards);
        }

        UserAccessBoardsService UserAccessBoardsService = new UserAccessBoardsService();
        List<Board> result = UserAccessBoardsService.findBoardsOfUser(EmailAddress.valueOf("isep123@gmail.com"), BoardRepository, eCourseUserRepository);
        Assertions.assertEquals(ownBoards, result);
    }
}