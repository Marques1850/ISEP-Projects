package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.app.backoffice.console.connection.SharedBoardServer;
import ecourse.base.boardManagment.ArchiveBoardController;
import ecourse.base.boardManagment.ShareBoardDto;

import java.util.List;

public class ArchiveBoardUI extends AbstractUI {
    private final ArchiveBoardController theController = SharedBoardServer.archiveBoard();
    @Override
    protected boolean doShow() {
        final List<ShareBoardDto> OwnedBoards = this.theController.getUserBoards();
        if(OwnedBoards.isEmpty()) {
            System.out.println("----------No Board Owned------------");
        } else{
            showBoard(OwnedBoards);
            int id=Integer.parseInt(OwnedBoards.get(OwnedBoards.size()-1).getId());
            boolean confirmation= false;
            confirmation = theController.ArchiveBoard(id);
            if(confirmation) {
                  System.out.println("Board Archived");
              }else{
                  System.out.println("Board Not Archived");
              }
        }
        return false;
    }

    private boolean showBoard(final List<ShareBoardDto> boards) {

        final Menu BoardMenu = buildBoardMenu(boards);
        final MenuRenderer renderer = new VerticalMenuRenderer(BoardMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildBoardMenu(final List<ShareBoardDto> boards) {
        final Menu BoardMenu = new Menu();
        int counter = 0;
        for (final ShareBoardDto board : boards) {
            BoardMenu.addItem(MenuItem.of(counter++, board.toString(), () -> boards.add(board)));
        }
        return BoardMenu;
    }
    @Override
    public String headline() {
        return "Archive Board";
    }
}
