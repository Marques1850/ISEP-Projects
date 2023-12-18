package ecourse.base.boardManagment;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import ecourse.base.PostItMagnament.ChangePostItController;
import ecourse.base.boardManagment.Event.BoardChangedEvent;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.time.LocalDateTime;

public class CreateBoardController {
    private final BoardRepository boardRepository = PersistenceContext.repositories().BoardRepository();
    private final eCourseUserRepository repoUser = PersistenceContext.repositories().eCourseUsers();

    static CreateBoardController instance;

    public static CreateBoardController getInstance() {
        if(instance == null){
            instance = new CreateBoardController();
        }
        return instance;
    }

    public boolean createBoard(String boardName, int rows, int columns) {
        eCourseSystemUser user = getUser();
        Board board = new Board(boardName, rows, columns, user);
        boardRepository.save(board);

        return true;
    }

    public eCourseSystemUser getUser(){

        final UserSession session = AuthzRegistry.authorizationService().session().get();
        return repoUser.searchUser(session.authenticatedUser().email()).get();


    }
}
