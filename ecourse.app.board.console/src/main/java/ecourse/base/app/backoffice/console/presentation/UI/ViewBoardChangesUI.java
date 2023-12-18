package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.boardManagment.BoardChangesDTO;
import ecourse.base.boardManagment.ShareBoardDto;
import ecourse.base.boardManagment.ViewBoardChangesController;
import ecourse.base.boardManagment.domain.Board;

import java.util.AbstractCollection;
import java.util.List;
import java.util.Objects;

public class ViewBoardChangesUI extends AbstractUI {
    private final ViewBoardChangesController theController = new ViewBoardChangesController();
    @Override
    protected boolean doShow() {
        final List<ShareBoardDto> boards = this.theController.getBoards();
        if (boards.isEmpty()) {
            System.out.println("----------No Boards are avaiable------------");
        } else {
            showBoards(boards);
            String Code = (boards.get(boards.size() - 1).getId());
            theController.getBoard(Code);
         List<BoardChangesDTO> changes=theController.getBoardChanges();
            if (changes.isEmpty()) {
                System.out.println("----------No Changes are avaiable------------");
            } else {
                for ( final BoardChangesDTO change : changes) {
                    System.out.println(change.toString());
                }
            }

        }
        return false;
    }

    private boolean showBoards(final List<ShareBoardDto> boards) {
        final Menu CourseMenu = buildBoardsMenu(boards);
        final MenuRenderer renderer = new VerticalMenuRenderer(CourseMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildBoardsMenu(final List<ShareBoardDto> boards) {
        final Menu CourseMenu = new Menu();
        int counter = 0;
        for (final ShareBoardDto board : boards  ) {
            CourseMenu.addItem(MenuItem.of(counter++, board.toString(), () -> boards.add(board)));
        }
        return CourseMenu;
    }
    @Override
    public String headline() {
        return "Board Changes ";
    }
}
