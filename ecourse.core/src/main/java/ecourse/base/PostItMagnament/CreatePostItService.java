package ecourse.base.PostItMagnament;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import ecourse.base.PostItMagnament.domain.Content;
import ecourse.base.PostItMagnament.domain.PostIt;
import ecourse.base.boardManagment.BoardChanges;
import ecourse.base.boardManagment.Event.BoardChangedEvent;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardChangesRepository;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import java.time.LocalDateTime;
import java.util.Optional;

public class CreatePostItService implements Runnable {

    private final BoardRepository boardRepository;
    private final BoardChangesRepository boardChangesRepository;
    private final EventPublisher publisher = InProcessPubSub.publisher();
    public CreatePostItService(BoardRepository boardRepository, BoardChangesRepository boardChangesRepository){
        this.boardRepository = boardRepository;
        this.boardChangesRepository = boardChangesRepository;
    }

    public PostItDto createPostIt(String postItRow, String postItColumn, String postItContent, String boardId, eCourseSystemUser user){

        Board board = createPostItSync(boardId, Integer.parseInt(postItRow), Integer.parseInt(postItColumn), postItContent, user);
        if(board == null){
            return null;
        }
        DomainEvent event=new BoardChangedEvent(new BoardChanges(board, LocalDateTime.now(),"PostIt with content: " + postItContent + " was created in row"+ postItRow + " and column " + postItColumn + " by user " + user.systemUserUsername() ,postItContent));
        publisher.publish(event);

        if(board.GetPostIt(Integer.parseInt(postItRow), Integer.parseInt(postItColumn) ) == null){
            return null;
        }

        return PostItMapper.toDto(board.GetPostIt(Integer.parseInt(postItRow), Integer.parseInt(postItColumn)));
    }

    private synchronized Board createPostItSync(String boardId, int postItRow, int postItColumn, String postItContent, eCourseSystemUser user){
        Board board = null;
        Optional<Board> boardOptional = boardRepository.findByCode(Long.parseLong(boardId));
        if (boardOptional.isPresent()) {
            board = boardOptional.get();
            try {
                PostIt postIt = new PostIt(new Content(postItContent), user);
                board.addCell(postIt, postItRow, postItColumn);
                boardRepository.save(board);
            } catch (Exception e) {
                System.out.println("Error creating post it: " + e.getMessage());
            }
        }
        return board;
    }

    @Override
    public void run() {

    }
}
