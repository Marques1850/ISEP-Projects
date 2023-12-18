package ecourse.base.boardManagment;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.PostItMagnament.UserAccessBoardsService;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardChangesRepository;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.ArrayList;
import java.util.List;

public class ViewBoardChangesController {
    private final BoardRepository theRepository = PersistenceContext.repositories().BoardRepository();

    private final BoardChangesRepository theBoardChangesRepository = PersistenceContext.repositories().BoardChangesRepository();
    private final eCourseUserRepository theUserRepository = PersistenceContext.repositories().eCourseUsers();

    private Board board;
    public List<ShareBoardDto> getBoards() {
        EmailAddress sessionEmail = AuthzRegistry.authorizationService().session().get().authenticatedUser().email();
        List<Board> boards =findBoardsOfUser(sessionEmail, theRepository, theUserRepository);
        List<ShareBoardDto> shareBoardDtos = new ArrayList<>();
        for (Board board: boards) {
            shareBoardDtos.add(ShareBoardMapper.toDto(board));
        }
        return shareBoardDtos;
    }
    public void getBoard(String boardId) {
        board = theRepository.findByCode(Long.parseLong(boardId)).get();
    }

    public List<Board> findBoardsOfUser(EmailAddress email, BoardRepository repo, eCourseUserRepository userRepository){
        eCourseSystemUser user = userRepository.searchUser(email).get();

        List<Board> ownBoards = repo.getallOwned(user);
        List<Board> sharedBoards = repo.findBoeardsHiShared(user);

        if(ownBoards.isEmpty() && sharedBoards.isEmpty()){
            return null;
        }else if(ownBoards.isEmpty()){
            return sharedBoards;
        }else if(sharedBoards.isEmpty()){
            return ownBoards;
        }else{
            ownBoards.addAll(sharedBoards);
            return ownBoards;
        }
    }

    public List<BoardChangesDTO> getBoardChanges() {
        List<BoardChanges> boardChanges = theBoardChangesRepository.findByBoard(board);
        List<BoardChangesDTO> boardChangesDTOS = new ArrayList<>();
        for (BoardChanges boardChange: boardChanges) {
            boardChangesDTOS.add(BoardChangesDTO.toDto(boardChange));
        }
        return boardChangesDTOS;
    }
}
