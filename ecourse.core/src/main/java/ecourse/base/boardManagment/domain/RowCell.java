package ecourse.base.boardManagment.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class RowCell {
    int row;
    String rowTitle;
    @Id
    private Long id;

    public RowCell(int row, String rowTitle) {
        this.row = row;
        this.rowTitle = rowTitle;
    }

    public RowCell() {

    }

    public String getRowTitle() {
        return rowTitle;
    }
    public int getRow() {
        return row;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Row Information[ " +
                "row = " + row +
                ", rowTitle = " + rowTitle +
                ", id = " + id +
                " ]";
    }
}
