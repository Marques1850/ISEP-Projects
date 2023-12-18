package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.domain.BoardStatus;
import ecourse.base.boardManagment.domain.BoardUserPermissions;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import java.util.*;

public class JpaBoardRepository extends JpaAutoTxRepository<Board, Long, Long> implements BoardRepository {

    public JpaBoardRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }
    public JpaBoardRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "id");
    }


    @Override
    public Optional<Board> findByCode(long id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("ID", id);
        return matchOne("id=:ID", params);
    }

    @Override
    public List<Board> getallOwned(eCourseSystemUser user) {
        final Map<String, Object> params = new HashMap<>();
        params.put("User", user);
        List<Board> boards=match("ownerOfTheBoard=:User", params);
        boards.removeIf(board -> board.status().equals(BoardStatus.ARCHIVED));
        return boards;
    }

    @Override
    public List<Board> findBoeardsHiShared(eCourseSystemUser user) {
        Iterable<Board> iterable = findAll();
        List<Board> boards = new ArrayList<>();
        for (Board board : iterable) {
            if(board.usersWithPermissions().containsKey(user)){
                    if(board.status().equals(BoardStatus.ACTIVE)) {
                        boards.add(board);
                    }

            }
        }

        return boards;
    }

    @Override
    public List<Board> findBoardsSharedWrite(eCourseSystemUser user) {
        Iterable<Board> iterable = findAll();
        List<Board> boards = new ArrayList<>();
        for (Board board : iterable) {
            if(board.usersWithPermissions().containsKey(user)){
                if(board.usersWithPermissions().get(user).compareTo(BoardUserPermissions.WRITE) == 0) {
                    if(board.status().equals(BoardStatus.ACTIVE)) {
                        boards.add(board);
                    }
                }
            }
        }

        return boards;
    }

}
