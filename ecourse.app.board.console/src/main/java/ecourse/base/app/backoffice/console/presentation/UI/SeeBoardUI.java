package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.app.backoffice.console.connection.server_ajax.SeeBoardController;
import ecourse.base.boardManagment.ShareBoardDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import java.awt.Desktop;
import java.net.URI;

import ecourse.base.app.backoffice.console.connection.server_ajax.HTTPmessage;
import java.io.DataOutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;


public class SeeBoardUI extends AbstractUI {

    private final SeeBoardController theController = new SeeBoardController();
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
            // Prompt user to enter IP address of server
            String serverIpAddress = Console.readLine("Enter IP address of server: ");

            theController.sendBoardInfo(selectedBoard, serverIpAddress);

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    sleep(3000);
                    Desktop.getDesktop().browse(new URI("http://" + serverIpAddress + ":8081/" + selectedBoard));
                } catch (IOException | URISyntaxException e) {
                    System.out.println("---------------|| Error opening the board ||---------------");
                } catch (InterruptedException e) {
                    System.out.println("---------------|| Error opening the board ||---------------");
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
        return "Show Board";
    }
}
