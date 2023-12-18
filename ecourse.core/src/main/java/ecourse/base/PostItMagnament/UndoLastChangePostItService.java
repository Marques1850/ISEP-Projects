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
import java.util.Optional;

public class UndoLastChangePostItService implements Runnable{
    private final BoardRepository boardRepository;
    private final BoardChangesRepository boardChangesRepository;
    private final EventPublisher publisher = InProcessPubSub.publisher();
    public UndoLastChangePostItService(BoardRepository boardRepository, BoardChangesRepository boardChangesRepository){
        this.boardRepository = boardRepository;
        this.boardChangesRepository = boardChangesRepository;
    }

    public PostItDto undoLastChangePostIt(String boardId, int row, int column, EmailAddress sessionEmail){
        Board board = undoLastChangePostItSync(boardId, row, column);
        if (board == null) return null;
        if (board.GetPostIt(row, column) == null) return null;
        if (board.GetPostIt(row, column).getContent() == null) return null;
        String content = board.GetPostIt(row, column).getContent().content();
        DomainEvent event = new BoardChangedEvent(
            new BoardChanges(   board,
                    LocalDateTime.now(),
                    "PostIt in pos [" + row + "," + column + "]: had undone change to: \'"+ content+ "\' by "+ sessionEmail+ "\n",
                    content
            ));
        publisher.publish(event);
        return PostItMapper.toDto(board.GetPostIt(row, column));
    }

    private synchronized Board undoLastChangePostItSync(String boardId, int row, int column){
        Board board = null;
        Optional<Board> boardOptional = boardRepository.findByCode(Long.parseLong(boardId));
        if (boardOptional.isPresent()) {
            board = boardOptional.get();
            String content = boardChangesRepository.getBoardPreviousContent(board);
            if (content != null) {
                try {
                    board.updateCell(content, row, column);
                    boardRepository.save(board);
                } catch (Exception e) {
                    System.out.println(" Couldn´t undo change !");
                }
            }
        }
        return board;
    }

    @Override
    public void run() {

    }
}