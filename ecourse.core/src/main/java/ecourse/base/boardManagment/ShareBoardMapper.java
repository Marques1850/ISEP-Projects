package ecourse.base.boardManagment;


import ecourse.base.boardManagment.domain.Board;

public class ShareBoardMapper {
    public static ShareBoardDto toDto(Board shareBoard) {
        return new ShareBoardDto(
                shareBoard.getId().toString(),
                shareBoard.boardName(),
                Integer.toString(shareBoard.maxRows()),
                Integer.toString(shareBoard.maxColumns()),
                shareBoard.ownerOfTheBoard().toString()
        );
    }

}
