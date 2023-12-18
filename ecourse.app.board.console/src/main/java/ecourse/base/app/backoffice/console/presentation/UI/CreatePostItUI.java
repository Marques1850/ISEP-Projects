package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.PostItMagnament.CreatePostItController;
import ecourse.base.PostItMagnament.PostItDto;
import ecourse.base.app.backoffice.console.connection.SharedBoardServer;
import ecourse.base.boardManagment.ShareBoardDto;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CreatePostItUI extends AbstractUI {

    Scanner sc = new Scanner(System.in);

    private final CreatePostItController theController = SharedBoardServer.createPostIt();

    @Override
    protected boolean doShow() {
        final List<ShareBoardDto> boards =theController.getUserBoards();

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

            PostItDto postItDto = null;
            try {
                PostItDto pst = theController.createPostIt(postItRow, postItColumn, postItContent, selectedBoard);
                if(pst != null){
                    System.out.println("Post-It created successfully");
                }
            } catch (InterruptedException e) {
                System.out.println("Post-It not created, because there is already a Post-It in that position");
                return false;
            }

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
        return "Create Post-It";
    }
}
