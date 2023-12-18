package ecourse.base.PostItMagnament;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.boardManagment.ShareBoardDto;
import ecourse.base.boardManagment.ShareBoardMapper;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.ArrayList;
import java.util.List;

public class ChangePostItController {

    private final BoardRepository theRepository = PersistenceContext.repositories().BoardRepository();
    private final eCourseUserRepository theUserRepository = PersistenceContext.repositories().eCourseUsers();
    private final UserAccessBoardsService UserAccessBoardsService = new UserAccessBoardsService();
    private static ChangePostItService service;

    static ChangePostItController instance;

    public static ChangePostItController getInstance() {
        if(instance == null){
            instance = new ChangePostItController();
        }
        return instance;
    }

    public ChangePostItController() {
        this.service = new ChangePostItService(
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

    public PostItDto changePostIt(String postItRow, String postItColumn, String postItContent, String Id) throws InterruptedException {
        EmailAddress sessionEmail = AuthzRegistry.authorizationService().session().get().authenticatedUser().email();
        return service.changePostIt(postItRow, postItColumn, postItContent, Id,sessionEmail);
    }


}
