package ecourse.base.boardManagment;

import java.util.List;

public class ShareBoardDto {

    private String id;

    private String boardName;

    private List<String> postIts;

    private String maxRows;

    private String maxColumns;

    private List<String> rowTitles;

    private List<String> columnTitles;

    private String ownerOfTheBoard;

    public ShareBoardDto(String id, String boardName, String maxRows, String maxColumns, String ownerOfTheBoard) {
        this.id = id;
        this.boardName = boardName;
        this.maxRows = maxRows;
        this.maxColumns = maxColumns;
        this.ownerOfTheBoard = ownerOfTheBoard;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ShareBoard [ " +
                "boardName=" + boardName +
                " | postIts=" + postIts +
                " | maxRows='" + maxRows +
                " | maxColumns='" + maxColumns +
                " | rowTitles=" + rowTitles +
                " | columnTitles=" + columnTitles +
                " | ownerOfTheBoard{" + ownerOfTheBoard +
                "} ]";
    }
}
