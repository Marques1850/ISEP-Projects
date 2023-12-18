package ecourse.base.PostItMagnament;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.RoleAssignment;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.PostItMagnament.domain.Content;
import ecourse.base.PostItMagnament.domain.PostIt;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardChangesRepository;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.ManagerBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.BaseRoles;
import org.antlr.v4.runtime.misc.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreatePostItServiceTest {

    private static CreatePostItService CreatePostItService;
    private static Board board;

    private static eCourseSystemUser user;


    @BeforeAll
    static void setUp() {
        ManagerBuilder mb = new ManagerBuilder();
        SystemUserBuilder systemUserBuilder = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        systemUserBuilder.withUsername("test");
        systemUserBuilder.withPassword("Test12");
        systemUserBuilder.withEmail("test@gmail.com");
        systemUserBuilder.withName("test", "test");
        systemUserBuilder.withRole(new RoleAssignment(BaseRoles.MANAGER));
        SystemUser systemUser = systemUserBuilder.build();

        mb.withSystemUser(systemUser);
        mb.withEmail("test@gmail.com");
        mb.withNIF("123456779");
        mb.withBirthDate("01/01/2000");
        eCourseSystemUser usr = mb.build();
        user = usr;
        board = new Board("teste", 10, 10,usr);
        board.getPostIts().put(new Pair<>(0, 0), new PostIt(new Content("teste"), usr));
        board.GetPostIt(0, 0).getContent().updatePostItContent("teste2");
        board.setIdForTests(Long.parseLong("1"));
        BoardRepository boardRepository = Mockito.mock(BoardRepository.class);
        BoardChangesRepository boardChangesRepository = mock(BoardChangesRepository.class);
        when(boardRepository.findByCode(1L)).thenReturn(Optional.of(board));
        when(boardRepository.findByCode(123)).thenReturn(Optional.empty());
        when(boardChangesRepository.getBoardPreviousContent(board)).thenReturn("teste");
        CreatePostItService = new CreatePostItService(boardRepository,boardChangesRepository);
    }

    @Test
    void testCreatePostItServiceWithInvalidRow() {
        assertNull(CreatePostItService.createPostIt("11", "5", "ola", "1", user));
    }
    @Test
    void testCreatePostItServiceWithInvalidBoardId() {
        assertNull(CreatePostItService.createPostIt("5", "5", "ola", "123", user));
    }
    @Test
    void testCreatePostItServiceWithInvalidColumn() {
        assertNull(CreatePostItService.createPostIt("5", "11", "ola", "1", user));
    }
    @Test
    void testCreatePostItServiceWithValidParams() {
        assertEquals("teste2", board.GetPostIt(0, 0).getContent().content());
        PostItDto postItDto = CreatePostItService.createPostIt("0", "0", "teste2", "1", user);
        assertEquals("teste2", postItDto.content());
    }

}