package ecourse.base.boardManagment.Event;

import ecourse.base.boardManagment.repositories.BoardChangesRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;

public class BoardChangesEventController {

  private final  BoardChangesRepository boardChangesRepository= PersistenceContext.repositories().BoardChangesRepository();
    public void saveChanges(BoardChangedEvent event) {
        boardChangesRepository.save(event.BoardChanges());
    }
}
