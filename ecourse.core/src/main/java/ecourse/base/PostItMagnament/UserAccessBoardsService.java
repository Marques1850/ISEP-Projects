package ecourse.base.PostItMagnament;

import eapli.framework.general.domain.model.EmailAddress;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.List;

public class UserAccessBoardsService {



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

    public List<Board> findBoardsOfUserWrite(EmailAddress email, BoardRepository repo, eCourseUserRepository userRepository){
        eCourseSystemUser user = userRepository.searchUser(email).get();

        List<Board> ownBoards = repo.getallOwned(user);
        List<Board> sharedBoards = repo.findBoardsSharedWrite(user);


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
}
