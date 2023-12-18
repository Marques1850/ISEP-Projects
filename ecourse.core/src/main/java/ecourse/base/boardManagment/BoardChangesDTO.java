package ecourse.base.boardManagment;

import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.domain.Exam;

public class BoardChangesDTO {
    public String Board;
    public String Date;
    public String Changes;

    public BoardChangesDTO(String board, String date, String changes) {
        Board = board;
        Date = date;
        Changes = changes;
    }

    public static BoardChangesDTO toDto(BoardChanges BoardChanges){
    return new BoardChangesDTO(BoardChanges.Board().toString(),BoardChanges.Date().toString(),BoardChanges.getBoardChanges());
}
    @Override
    public String toString() {
        return " Date='" + Date + '\'' +
                ", Changes='" + Changes + '\'';
    }
}
