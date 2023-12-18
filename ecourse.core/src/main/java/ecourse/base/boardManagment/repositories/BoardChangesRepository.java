package ecourse.base.boardManagment.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import ecourse.base.boardManagment.BoardChanges;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BoardChangesRepository extends DomainRepository<Long, BoardChanges> {

  List<BoardChanges> findByBoard(Board board);

  List<BoardChanges> findByDate(LocalDate date);

  String getBoardPreviousContent(Board board);

}
