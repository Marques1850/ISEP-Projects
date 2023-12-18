package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.app.backoffice.console.connection.SharedBoardServer;
import ecourse.base.boardManagment.CreateBoardController;

public class CreateBoardUI extends AbstractUI{
    private final CreateBoardController theController = SharedBoardServer.createBoard();

    @Override
    protected boolean doShow(){
        String boardName = Console.readLine("Enter Board Name: ");
        int maxRows= Console.readInteger("Enter Max Rows: ");
        int maxColumns= Console.readInteger("Enter Max Columns: ");
        theController.createBoard(boardName, maxRows, maxColumns);

        System.out.println("Board created successfully");
        return true;

    }
        @Override
        public String headline() {
            return "Create Board";
        }
}


