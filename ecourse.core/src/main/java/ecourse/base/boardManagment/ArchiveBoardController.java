package ecourse.base.boardManagment;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import ecourse.base.PostItMagnament.CreatePostItController;
import ecourse.base.boardManagment.Event.BoardChangedEvent;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArchiveBoardController {

    private BoardRepository boardRepository= PersistenceContext.repositories().BoardRepository();
    private final eCourseUserRepository repo = PersistenceContext.repositories().eCourseUsers();
    private final EventPublisher publisher = InProcessPubSub.publisher();
    private Board board;

    static ArchiveBoardController instance;

    public static ArchiveBoardController getInstance() {
        if(instance == null){
            instance = new ArchiveBoardController();
        }
        return instance;
    }

    public List<ShareBoardDto> getUserBoards() {

        List<Board> boards=boardRepository.getallOwned(defineUser());
        List<ShareBoardDto> boardDtos=new ArrayList<>();
        for ( Board board: boards) {
            boardDtos.add(ShareBoardMapper.toDto(board)) ;
        }

        return boardDtos;
    }

    public synchronized boolean ArchiveBoard(int id) {
        board = boardRepository.findByCode((long) id).get();
        boolean result = board.archive();
        if (result) {
            boardRepository.save(board);
            DomainEvent event=new BoardChangedEvent(new BoardChanges(board, LocalDateTime.now(),"Board was archived"));
           publisher.publish(event);
            return true;
        }
        return false;
    }
    public eCourseSystemUser defineUser() {
        final UserSession session =  AuthzRegistry.authorizationService().session().get();
        SystemUser systemUser = session.authenticatedUser();
        return repo.searchUser(systemUser.email()).get();

    }
}
