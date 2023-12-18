package ecourse.base.boardManagment;

import eapli.framework.domain.model.AggregateRoot;
import ecourse.base.boardManagment.domain.Board;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
@Table(name = "BOARDCHANGES")
public class BoardChanges implements AggregateRoot<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_CHANGE_ID")
    private Long id;
    @OneToOne(cascade = {CascadeType.ALL})
    Board boardChanged;
    @Column(name = "DATE_OF_CHANGE")
    LocalDateTime date;
    @Column(name ="BOARDCHANGES",columnDefinition="text")
    String BoardChanges;
    @Column(name ="BOARDCHANGED_CONTENT",columnDefinition="text")
    String content;

    public BoardChanges(Board board, LocalDateTime date, String boardChanges) {
        this.boardChanged = board;
        this.date = date;
        this.BoardChanges = boardChanges;
    }
    public BoardChanges(Board board, LocalDateTime date, String boardChanges,String content) {
        this.boardChanged = board;
        this.date = date;
        this.BoardChanges = boardChanges;
        this.content=content;
    }

    public BoardChanges() {
    }
    @Override
    public boolean sameAs(Object other) {
        return this == other;
    }

    @Override
    public Long identity() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardChanges)) return false;
        BoardChanges that = (BoardChanges) o;
        return Objects.equals(id, that.id) && Objects.equals(boardChanged, that.boardChanged) && Objects.equals(date, that.date) && Objects.equals(BoardChanges, that.BoardChanges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, boardChanged, date, BoardChanges);
    }

    public Board Board() {
        return boardChanged;
    }

    public LocalDateTime Date() {
        return date;
    }

    public String getBoardChanges() {
        return BoardChanges;
    }

    public String content() {
        return content;
    }
}
