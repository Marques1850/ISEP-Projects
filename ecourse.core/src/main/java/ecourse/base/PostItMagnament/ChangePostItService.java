package ecourse.base.PostItMagnament;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import ecourse.base.boardManagment.BoardChanges;
import ecourse.base.boardManagment.Event.BoardChangedEvent;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardChangesRepository;
import ecourse.base.boardManagment.repositories.BoardRepository;

import java.time.LocalDateTime;

public class ChangePostItService {
    private final BoardRepository boardRepository;
    private final BoardChangesRepository boardChangesRepository;
    private final EventPublisher publisher = InProcessPubSub.publisher();
    public ChangePostItService(BoardRepository boardRepository, BoardChangesRepository boardChangesRepository){
        this.boardRepository = boardRepository;
        this.boardChangesRepository = boardChangesRepository;
    }

    public PostItDto changePostIt(String postItRow, String postItColumn, String postItContent, String Id, EmailAddress sessionEmail){
        Board board = changePostItSync(postItRow, postItColumn, postItContent, Id);
        if ( board == null ) {
            return null;
        }
        if (board.GetPostIt(Integer.parseInt(postItRow), Integer.parseInt(postItColumn)) == null) return null;
        if (board.GetPostIt(Integer.parseInt(postItRow),Integer.parseInt(postItColumn)).getContent() == null) return null;
        DomainEvent event=new BoardChangedEvent(new BoardChanges(board,
                LocalDateTime.now(),
                "PostIt in pos [" + postItRow + "," + postItColumn + "]: content changed to \'"+postItContent+"\' by "+sessionEmail+"\n",
                postItContent));
        publisher.publish(event);
        return PostItMapper.toDto(board.GetPostIt(Integer.valueOf(postItRow),Integer.valueOf(postItColumn)));
    }

    private synchronized Board changePostItSync(String postItRow, String postItColumn, String postItContent, String Id){
        Board board = null;
        try {
            board = boardRepository.findByCode(Long.parseLong(Id)).get();
            board.updateCell(postItContent, Integer.parseInt(postItRow), Integer.parseInt(postItColumn));
            boardRepository.save(board);
        } catch (IllegalStateException e) {
            System.out.println("Cell has no post-it to update");
        }
        return board;
    }


}
