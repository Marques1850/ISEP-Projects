package ecourse.base.boardManagment.Event;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.pubsub.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardChangeWatchDog implements EventHandler {

    private static final Logger logger= LoggerFactory.getLogger(BoardChangeWatchDog.class);

    @Override
    public void onEvent(DomainEvent domainEvent) {
        assert domainEvent instanceof BoardChangedEvent;

        final BoardChangedEvent event = (BoardChangedEvent) domainEvent;

        final BoardChangesEventController controller = new BoardChangesEventController();
        try{
            System.out.println("Board changed event received");
            controller.saveChanges(event);

        }catch (final IntegrityViolationException e){
            logger.error("Integrity violation exception while handling board changed event",e);
        }

    }
}
