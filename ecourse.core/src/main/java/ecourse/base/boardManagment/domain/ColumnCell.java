package ecourse.base.boardManagment.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class ColumnCell{
    int column;
    String columnTitle;
    @Id
    private Long id;

    public ColumnCell(int column, String columnTitle) {
        this.column = column;
        this.columnTitle = columnTitle;
    }

    public ColumnCell() {

    }

    public String getColumnTitle() {
        return columnTitle;
    }
    public int getColumn() {
        return column;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Column information[ " +
                "column = " + column +
                ", columnTitle = " + columnTitle +
                ", id = " + id +
                " ]";
    }
}
