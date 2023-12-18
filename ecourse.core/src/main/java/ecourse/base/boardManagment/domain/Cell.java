package ecourse.base.boardManagment.domain;

import javax.persistence.*;

@Entity
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CELL_ID") //the id of the cell
    private long id;

    private String content;

    public Cell() {
        // for ORM only
    }
    public boolean isBelowOr20AndAboveOr1(int num) {
        return (num <=20 && num >= 1);
    }

    public boolean isBelowOr10AndAboveOr1(int num) {
        return (num <=10 && num >= 1);
    }

}
