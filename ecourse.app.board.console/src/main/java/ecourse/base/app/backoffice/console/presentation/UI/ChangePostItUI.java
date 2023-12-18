package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.PostItMagnament.ChangePostItController;
import ecourse.base.PostItMagnament.PostItDto;
import ecourse.base.app.backoffice.console.connection.SharedBoardServer;
import ecourse.base.boardManagment.ShareBoardDto;
import ecourse.base.boardManagment.domain.Board;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ChangePostItUI extends AbstractUI {

    Scanner sc = new Scanner(System.in);

    private final ChangePostItController theController = SharedBoardServer.changePostIt();

    @Override
    protected boolean doShow() {
        List<ShareBoardDto> boards = null;
        try{
            boards =theController.getUserBoards();
        }catch (NullPointerException e) {
            System.out.println("---------------|| You don't own and/or shared any boards ||---------------");
            return false;
        }


        if(boards.isEmpty()){
            System.out.println("---------------|| You don't own and/or shared any boards ||---------------");
            return false;
        }else {
            String selectedBoard;
            String option;


            showBoards(boards);
            String Code = (boards.get(boards.size() - 1).getId());

            selectedBoard = Code;
            boards.removeIf(t -> Objects.equals(t.getId(), Code));



            String postItRow = Console.readLine("Post-It Row:");
            String postItColumn = Console.readLine("Post-It Column:");
            String postItContent = Console.readLine("Post-It Content:");

            try {
                theController.changePostIt(postItRow, postItColumn, postItContent, selectedBoard);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Post-It changed successfully");


            return false;
        }

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
        return "Change Post-It";
    }
}
