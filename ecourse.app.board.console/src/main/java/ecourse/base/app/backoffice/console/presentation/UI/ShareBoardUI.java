package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.app.backoffice.console.connection.SharedBoardServer;
import ecourse.base.boardManagment.ShareBoardController;
import ecourse.base.boardManagment.ShareBoardDto;
import ecourse.base.boardManagment.domain.BoardUserPermissions;

import java.util.Arrays;
import java.util.List;

public class ShareBoardUI extends eapli.framework.presentation.console.AbstractUI {
    private final ShareBoardController theController = SharedBoardServer.shareBoard();

    @Override
    protected boolean doShow() {
        final List<ShareBoardDto> OwnedBoards = this.theController.getOwnedBoards();
        if(OwnedBoards.isEmpty()) {
            System.out.println("----------No Board Owned------------");
        } else{
               showBoard(OwnedBoards);
               int id=Integer.parseInt(OwnedBoards.get(OwnedBoards.size()-1).getId());
              String option="";
               do{
                 String email= Console.readLine("Enter the email of the user you want to share the board with(Write 'exit' to leave)");
                    if(email.equals("exit")) {
                        option = "exit";
                    }else{
                        if(!this.theController.ValidateEmail(email))continue;
                        List<BoardUserPermissions> perms= Arrays.asList(BoardUserPermissions.values());

                         BoardUserPermissions permission=showBoardPermissions(perms);
                         boolean result = false;
                        result = theController.shareBoard(id,permission,email);
                        if (result) {
                            System.out.println(email + " has been given " +permission+ " permissions to the board \n\n");
                        } else {
                            System.out.println("Board Sharing Failed \n \n");
                        }

                    }

               }while(!option.equals("exit"));

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
    private BoardUserPermissions showBoardPermissions(final List<BoardUserPermissions> perms) {
        int option=-1;
        System.out.println("----------Board Permissions------------");
        System.out.println("0." + perms.get(0).toString());
        System.out.println("1." + perms.get(1).toString());
        do {
            option = Console.readInteger("Choose the permission you want to give to the user");
        }while(option<0 || option>1);

        return perms.get(option);
    }



    @Override
    public String headline() {
        return "Share Board";
    }

}
