package ecourse.base.boardManagment;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import ecourse.base.boardManagment.Event.BoardChangedEvent;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.domain.BoardUserPermissions;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShareBoardController {

    private eCourseUserRepository userRepository= PersistenceContext.repositories().eCourseUsers();
    private BoardRepository boardRepository= PersistenceContext.repositories().BoardRepository();
    private final EventPublisher publisher = InProcessPubSub.publisher();

    static ShareBoardController instance;

    public static ShareBoardController getInstance() {
        if(instance == null){
            instance = new ShareBoardController();
        }
        return instance;
    }


    public List<ShareBoardDto> getOwnedBoards() {
        SystemUser user=getUser();
        eCourseSystemUser euser=userRepository.searchUser(user.email()).get();
        List<Board> boards=boardRepository.getallOwned(euser);
        List<ShareBoardDto> boardDtos=new ArrayList<>();
        for ( Board board: boards) {
           boardDtos.add(ShareBoardMapper.toDto(board)) ;
        }

        return boardDtos;
    }

    public boolean ValidateEmail(String email) {
        if(userRepository.searchUser(EmailAddress.valueOf(email)).isPresent()){
            return true;
        }
        return false;

    }

    public synchronized boolean shareBoard(int id, BoardUserPermissions permission,String email) {
        Board board=boardRepository.findByCode(id).get();
        eCourseSystemUser user1 = userRepository.searchUser(EmailAddress.valueOf(email)).get();
        board.shareBoard(user1,permission);
        boardRepository.save(board);
        DomainEvent event=new BoardChangedEvent(new BoardChanges(board, LocalDateTime.now(),user1.systemUserUsername()+" was given "+permission+" permissions","permitions added" ));
        publisher.publish(event);
        return true;

    }
    public SystemUser getUser(){
        final UserSession session = AuthzRegistry.authorizationService().session().get();
        return session.authenticatedUser();
    }
}
