package ecourse.base.boardManagment.Event;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import ecourse.base.boardManagment.BoardChanges;

public class BoardChangedEvent extends DomainEventBase implements DomainEvent {
    private BoardChanges boardChanges;

    public BoardChangedEvent(BoardChanges boardChanges) {
        this.boardChanges = boardChanges;
    }

    public BoardChanges BoardChanges() {
        return boardChanges;
    }
}
