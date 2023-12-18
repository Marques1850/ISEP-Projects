package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.boardManagment.BoardChanges;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.domain.BoardStatus;
import ecourse.base.boardManagment.domain.BoardUserPermissions;
import ecourse.base.boardManagment.repositories.BoardChangesRepository;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import java.time.LocalDate;
import java.util.*;

public class JpaBoardChangesRepository extends JpaAutoTxRepository<BoardChanges, Long, Long> implements BoardChangesRepository {

    public JpaBoardChangesRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }
    public JpaBoardChangesRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "id");
    }


    @Override
    public List<BoardChanges> findByBoard(Board board) {
        final Map<String, Object> params = new HashMap<>();
        params.put("Board", board);
        return match("e.boardChanged = :Board", params);
    }

    @Override
    public List<BoardChanges> findByDate(LocalDate date) {
        return null;
    }

    @Override
    public String getBoardPreviousContent(Board board) {
        List<BoardChanges> boardChangesList = findByBoard(board);
        if(boardChangesList.size() <= 1){
            return null;
        } else {
            Collections.sort(boardChangesList, new Comparator<BoardChanges>() {
                @Override
                public int compare(BoardChanges change1, BoardChanges change2) {
                    return change1.Date().compareTo(change2.Date());
                }
            });
            // boardChangesList.size()-2  :because the last change is the current one
            return boardChangesList.get(boardChangesList.size()-2).content();
        }
    }
}
