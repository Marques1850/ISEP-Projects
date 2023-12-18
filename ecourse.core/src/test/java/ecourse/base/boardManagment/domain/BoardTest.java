package ecourse.base.boardManagment.domain;

import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourse.base.PostItMagnament.domain.Content;
import ecourse.base.PostItMagnament.domain.PostIt;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.TeacherBuilder;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
    private Board board1;
    private static eCourseSystemUser user1;





    @BeforeAll
    static  void  setUpAll() {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        TeacherBuilder tB = new TeacherBuilder();
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("johndoe@example.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser = sB.build();

        tB.withSystemUser(systemUser);
        tB.withEmail("johndoe@example.com");
        tB.withAcronym("JHA");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");
       user1 = tB.build();
    }

    @BeforeEach
    void setUp() {


    board1 = new Board("test", 20, 10,user1);
    }



    @Test
    void addRowTitleOutsideBoard() {

        Assertions.assertThrows(IllegalStateException.class, () -> board1.addRowTitle(new RowCell( 25,"balls")));

    }

    @Test
    void addRowTitlelAlreadyUsedPlaceBoard() {
        board1.addRowTitle(new RowCell( 19,"balls1"));
        Assertions.assertThrows(IllegalStateException.class, () -> board1.addRowTitle(new RowCell( 19,"balls")));
    }

    @Test
    void addRowTitleTest() {
        board1.addRowTitle(new RowCell( 18,"balls1"));
        Assertions.assertEquals(1,board1.getRowTitles().size());

    }


    @Test
    void addColumnTitleOutsideBoard() {

        Assertions.assertThrows(IllegalStateException.class, () -> board1.addColumnTitle(new ColumnCell( 25,"balls")));

    }

    @Test
    void addColumnTitlelAlreadyUsedPlaceBoard() {
        board1.addColumnTitle(new ColumnCell( 9,"balls1"));
        Assertions.assertThrows(IllegalStateException.class, () -> board1.addColumnTitle(new ColumnCell( 19,"balls")));
    }

    @Test
    void addColumnTitleTest() {
        board1.addColumnTitle(new ColumnCell( 9,"balls1"));
        Assertions.assertEquals(1,board1.getColumnTitles().size());

    }


    @Test
    void shareBoardTest() {
        SystemUserBuilder sB = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        TeacherBuilder tB = new TeacherBuilder();
        sB.withName("John", "Doe");
        sB.withUsername("johndoe");
        sB.withEmail("johndoe@example1.com");
        sB.withPassword("Password22");
        sB.withRoles(Role.valueOf("TEACHER"));
        SystemUser systemUser = sB.build();

        tB.withSystemUser(systemUser);
        tB.withEmail("johndoe@example1.com");
        tB.withAcronym("JHE");
        tB.withNIF("123456786");
        tB.withBirthDate("01/01/1990");
       eCourseSystemUser user2 = tB.build();
        board1.shareBoard(user2,BoardUserPermissions.WRITE);
        Assertions.assertTrue(board1.usersWithPermissions().containsKey(user2));
        Assertions.assertTrue(board1.usersWithPermissions().containsValue(BoardUserPermissions.WRITE));

    }

    @Test
    void checkPossiblePostItPlacementTest() {
          Assertions.assertTrue(board1.CheckPostItPlacement(1,1));
    }


    @Test
    void archiveCorrectTest() {
        board1.archive();
        Assertions.assertEquals(board1.status(), BoardStatus.ARCHIVED);
    }

    @Test
    void archiveAlreadyArchivedTest() {
        board1.archive();
        Assertions.assertFalse(board1.archive());

    }



}