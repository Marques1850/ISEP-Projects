package ecourse.base.boardManagment.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends DomainRepository<Long, Board> {

    Optional<Board> findByCode(long id);

    List<Board> getallOwned(eCourseSystemUser user);

    List<Board> findBoeardsHiShared(eCourseSystemUser user);
    List<Board> findBoardsSharedWrite(eCourseSystemUser user);

}
