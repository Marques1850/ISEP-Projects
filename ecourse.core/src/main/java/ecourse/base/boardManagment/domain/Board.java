package ecourse.base.boardManagment.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourse.base.PostItMagnament.domain.PostIt;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import org.antlr.v4.runtime.misc.Pair;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "BOARD")
public class Board implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "BOARD_NAME", unique = true)
    private String boardName;

    @OneToMany(cascade = {CascadeType.ALL})
    @MapKeyColumn(name = "PostIt")
    private Map<Pair<Integer,Integer>, PostIt> postIts;

    @Column(name = "max_rows")
    private int maxRows;

    @Column(name = "max_columns")
    private int maxColumns;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<RowCell> rowTitles;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<ColumnCell> columnTitles;

    @OneToOne
    private eCourseSystemUser ownerOfTheBoard;
    @ElementCollection
    @MapKeyColumn(name = "User_Permissions")
    private Map<eCourseSystemUser, BoardUserPermissions> userPermissions;
    @Column(name = "BOARD_STATUS")
    private BoardStatus boardStatus;

    public Board(String boardName, int maxRows, int maxColumns, eCourseSystemUser ownerOfTheBoard) {

        this.boardName = boardName;

        this.maxRows = maxRows;
        this.maxColumns = maxColumns;
        this.ownerOfTheBoard = ownerOfTheBoard;
        this.userPermissions = new HashMap<>();
        this.postIts = new HashMap<>();
        this.rowTitles = new ArrayList<>();
        this.columnTitles = new ArrayList<>();
        this.boardStatus = BoardStatus.ACTIVE;

    }

    public Board() {
        this.userPermissions = new HashMap<>();
        this.postIts = new HashMap<>();
        this.rowTitles = new ArrayList<>();
        this.columnTitles = new ArrayList<>();
        this.boardStatus = BoardStatus.ACTIVE;
    }

    public void addCell(PostIt postIt, int row, int column){
        if(row > maxRows || column > maxColumns){
            throw new IllegalStateException("Post-It is on a cell that is out of bounds");
        }

        if (CheckPostItPlacement(row, column)) {
            postIts.put(new Pair<>(row, column), postIt);
        } else {
            throw new IllegalStateException("Post-It is on a cell that is already occupied");
        }


    }

    public void removeCell(PostIt postIt){
        postIts.remove(postIt);
    }

    public void updateCell(String newContent, int row, int column){
        if (CheckPostItPlacementUpdate(row, column)) {
            postIts.get(new Pair<>(row, column)).getContent().updatePostItContent(newContent);
        } else {
            throw new IllegalStateException("Cell has no post-it to update");
        }
    }

    public void addRowTitle(RowCell rowCell){
        if(rowCell.getRow() > maxRows){
            throw new IllegalStateException("Row is out of bounds");
        }
        boolean rowCellExists = rowTitles.stream().anyMatch(c -> c.getRow() == rowCell.getRow());
        if(rowCellExists){
            throw new IllegalStateException("Row already exists, please use updateRow");
        }
        rowTitles.add(rowCell);
    }

    public void removeRowTitle(RowCell rowCell){
        rowTitles.remove(rowCell);
    }

    public void updateRowTile(RowCell rowCell){
        boolean rowCellExists = rowTitles.stream().anyMatch(c -> c.getRow() == rowCell.getRow());
        if(!rowCellExists){
            throw new IllegalStateException("Row does not exist, please use addRow");
        }
        rowTitles.remove(rowCell);
        rowTitles.add(rowCell);
    }

    public void addColumnTitle(ColumnCell columnCell){
        if(columnCell.getColumn() > maxColumns){
            throw new IllegalStateException("Column is out of bounds");
        }

        boolean columnCellExists = columnTitles.stream().anyMatch(c -> c.getColumn() == columnCell.getColumn());
        if(columnCellExists){
            throw new IllegalStateException("Column already exists, please use updateColumn");
        }
        columnTitles.add(columnCell);
    }

    public void removeColumnTitle(ColumnCell columnCell){
        columnTitles.remove(columnCell);
    }

    public void updateColumnTitle(ColumnCell columnCell){
        boolean columnCellExists = columnTitles.stream().anyMatch(c -> c.getColumn() == columnCell.getColumn());
        if(!columnCellExists){
            throw new IllegalStateException("Column does not exist, please use addColumn");
        }
        columnTitles.remove(columnCell);
        columnTitles.add(columnCell);
    }
    
    public void removeSharedUser(eCourseSystemUser user){
        userPermissions.remove(user);
    }

    public Long getId() {
        return id;
    }

    public String getBoardName() {
        return boardName;
    }

    public Map<Pair<Integer,Integer>, PostIt> getPostIts() {
        return postIts;
    }
    public List<RowCell> getRowTitles() {
        return rowTitles;
    }
    public List<ColumnCell> getColumnTitles() {
        return columnTitles;
    }
    public eCourseSystemUser getOwnerOfTheBoard() {
        return ownerOfTheBoard;
    }


    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public int compareTo(Long other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Long identity() {
        return null;
    }

    @Override
    public boolean hasIdentity(Long id) {
        return AggregateRoot.super.hasIdentity(id);
    }

    public String boardName() {
        return boardName;
    }

    public int maxRows() {
        return maxRows;
    }

    public int maxColumns() {
        return maxColumns;
    }

    public eCourseSystemUser ownerOfTheBoard() {
        return ownerOfTheBoard;
    }
    public boolean shareBoard(eCourseSystemUser user, BoardUserPermissions permissions){
        userPermissions.put(user, permissions);
        return true;
    }

    public Map<eCourseSystemUser, BoardUserPermissions> usersWithPermissions(){
        return userPermissions;
    }

    public void setIdForTests(Long id) {
        this.id = id;
    }

    public boolean CheckPostItPlacement(int row, int collum){
        if(row > maxRows || collum > maxColumns){
            throw new IllegalStateException("Post-It is on a cell that is out of bounds");
        }
        if(!postIts.containsKey(new Pair<>(row, collum)) || postIts.get(new Pair<>(row, collum)) == null){
            System.out.println("Post-It is on a cell that is not occupied");
            return true;
        } else {
            System.out.println("Post-It is on a cell that is already occupied");
            return false;
        }
    }

    public boolean CheckPostItPlacementUpdate(int row, int collum){
        if(row > maxRows || collum > maxColumns){
            throw new IllegalStateException("Post-It is on a cell that is out of bounds");
        }
        if(postIts.containsKey(new Pair<>(row, collum))){
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean archive(){
        if(this.boardStatus == BoardStatus.ARCHIVED){
            System.out.println("Board is already archived");
            return false;
        }
        this.boardStatus = BoardStatus.ARCHIVED;
        return true;
    }
    public BoardStatus status(){
        return boardStatus;
    }


    public PostIt GetPostIt(int row, int collumn){
        return postIts.get(new Pair<>(row, collumn));
    }

    public int getMaxRows() {
        return maxRows;
    }

    public int getMaxColumns() {
        return maxColumns;
    }


}
