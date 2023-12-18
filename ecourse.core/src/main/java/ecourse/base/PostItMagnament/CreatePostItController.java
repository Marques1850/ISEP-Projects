package ecourse.base.PostItMagnament;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import ecourse.base.boardManagment.ShareBoardDto;
import ecourse.base.boardManagment.ShareBoardMapper;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.ArrayList;
import java.util.List;

public class CreatePostItController{

    private final BoardRepository theRepository = PersistenceContext.repositories().BoardRepository();
    private final eCourseUserRepository theUserRepository = PersistenceContext.repositories().eCourseUsers();
    private final EventPublisher publisher = InProcessPubSub.publisher();

    private final UserAccessBoardsService UserAccessBoardsService = new UserAccessBoardsService();
    private static Board board;

    static CreatePostItController instance;

    private static CreatePostItService service;

    public static CreatePostItController getInstance() {
        if(instance == null){
            instance = new CreatePostItController();
        }
        return instance;
    }

    public CreatePostItController() {
        service = new CreatePostItService(
                PersistenceContext.repositories().BoardRepository(),
                PersistenceContext.repositories().BoardChangesRepository()
        );
    }

    public List<ShareBoardDto> getUserBoards() {
        EmailAddress sessionEmail = AuthzRegistry.authorizationService().session().get().authenticatedUser().email();
        List<Board> boards = UserAccessBoardsService.findBoardsOfUserWrite(sessionEmail, theRepository, theUserRepository);
        List<ShareBoardDto> shareBoardDtos = new ArrayList<>();
        for (Board board: boards) {
            shareBoardDtos.add(ShareBoardMapper.toDto(board));
        }
        return shareBoardDtos;
    }

    public PostItDto createPostIt(String postItRow, String postItColumn, String postItContent, String boardId) throws InterruptedException {
        EmailAddress sessionEmail = AuthzRegistry.authorizationService().session().get().authenticatedUser().email();
        eCourseSystemUser user = theUserRepository.searchUser(sessionEmail).get();

        return service.createPostIt(postItRow, postItColumn, postItContent, boardId, user);
    }
}
