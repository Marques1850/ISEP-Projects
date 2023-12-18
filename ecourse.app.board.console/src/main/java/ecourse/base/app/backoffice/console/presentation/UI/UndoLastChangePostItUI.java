package ecourse.base.app.backoffice.console.presentation.UI;


import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.PostItMagnament.ChangePostItController;
import ecourse.base.PostItMagnament.PostItDto;
import ecourse.base.app.backoffice.console.connection.SharedBoardServer;
import ecourse.base.PostItMagnament.UndoLastChangePostItController;
import ecourse.base.boardManagment.ShareBoardDto;

import java.util.List;

import static java.lang.Integer.parseInt;

public class UndoLastChangePostItUI extends AbstractUI {
    private final ChangePostItController controller2 = new ChangePostItController();
    private final UndoLastChangePostItController controller = SharedBoardServer.undoLastChangePostIt();
    @Override
    protected boolean doShow() {
        List<ShareBoardDto> boards;
        try{
            boards = controller2.getUserBoards();
        }catch (NullPointerException e) {
            System.out.println("---------------|| You don't own and/or shared any boards ||---------------");
            return false;
        }

        if( !boards.isEmpty() ){
            String boardID = displayBoards( boards );

            int row, column;
            while (true){
                try {
                    String rowStr = Console.readLine("Select row :");
                    row = Integer.parseInt(rowStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid option");
                }
            }

            while (true) {
                try {
                    String columnStr = Console.readLine("Select column :");
                    column = Integer.parseInt(columnStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid option");
                }
            }

            PostItDto dto = controller.undoLastChangePostIt(boardID, row, column);
            if ( dto != null ) {
                System.out.println("Undo Last Change Post-It :");
                System.out.println("====================================");
                System.out.println(dto);
                System.out.println("====================================");
            } else {
                System.out.println("---------------|| No changes to undo ||---------------");
            }

        } else {
            System.out.println("--|| You don't own and/or shared any boards ||--");
            return false;
        }
        return false;
    }

    private String displayBoards( List<ShareBoardDto> boards ) {
        String scan;
        int option;
        System.out.println("Boards :");
        System.out.println("====================================");
        int i = 0;
        for (ShareBoardDto board : boards) {
            System.out.printf("%d - %s\n", i, board.toString());
            i++;
        }
        System.out.println("====================================");
        while (true){
            try {
                scan = Console.readLine("Select shared board :");
                option = parseInt(scan);
                if ( option < 0 || option >= boards.size() ) {
                    System.out.println("Invalid option");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option");
            }
        }
        return boards.get(option).getId();
    }


    @Override
    public String headline() {
        return "Undo Last Change Post-It";
    }
}
