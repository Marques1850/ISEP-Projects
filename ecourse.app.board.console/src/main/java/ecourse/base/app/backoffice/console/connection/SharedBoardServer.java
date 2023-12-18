package ecourse.base.app.backoffice.console.connection;


import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import ecourse.base.PostItMagnament.ChangePostItController;
import ecourse.base.PostItMagnament.CreatePostItController;
import ecourse.base.PostItMagnament.PostItDto;
import ecourse.base.app.backoffice.console.connection.server_ajax.HttpAjaxBoardRequest;
import ecourse.base.app.backoffice.console.connection.util.Constants;
import ecourse.base.PostItMagnament.UndoLastChangePostItController;
import ecourse.base.boardManagment.ArchiveBoardController;
import ecourse.base.boardManagment.CreateBoardController;
import ecourse.base.boardManagment.ShareBoardController;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.domain.BoardUserPermissions;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class  SharedBoardServer {
    private static Map<String, String> boards = new HashMap<>();
    private ServerSocket serverSocket;


    //Controllers
    private static final CreatePostItController thePostItController = CreatePostItController.getInstance();
    private static final CreateBoardController theBoardController = CreateBoardController.getInstance();
    private static final ChangePostItController theChangePostItController = ChangePostItController.getInstance();
    private static final UndoLastChangePostItController theLatChangeController = UndoLastChangePostItController.getInstance();
    private static final ShareBoardController theSharedController = ShareBoardController.getInstance();
    private static final ArchiveBoardController theArchiveController = ArchiveBoardController.getInstance();
    private static BoardRepository boardRepository = PersistenceContext.repositories().BoardRepository();


    public SharedBoardServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket( Constants.SERVER_PORT );
        ServerSocket serverSocketApp = new ServerSocket( Constants.SERVER_PORT_APP );
        SharedBoardServer server = new SharedBoardServer(serverSocket);
        SharedBoardServer serverApp = new SharedBoardServer(serverSocketApp);
        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(), new PlainTextEncoder());

        // Print IP address of server
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("IP Address: " + inetAddress.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.startServer( serverSocket );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverApp.startServer( serverSocketApp );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        ServerSocket ajaxServerSocket = new ServerSocket( Constants.AJAX_SERVER_PORT );

        Socket cliSock;

        while (true) {
            cliSock = ajaxServerSocket.accept();
            HttpAjaxBoardRequest req = new HttpAjaxBoardRequest(cliSock, Constants.BASE_FOLDER);
            req.start();
        }
    }

    private void startServer(ServerSocket serverSocket) throws IOException {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                if ( serverSocket.getLocalPort() == Constants.SERVER_PORT )
                    new Thread(new ServerClientHandler(socket)).start();
            }
        } catch (IOException ex) {
            System.out.println("Failed to accept client connection");
            closeServerSocket(serverSocket);
        }
    }

    private void closeServerSocket( ServerSocket serverSocket) throws IOException {
        try {
            if ( serverSocket != null ) serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Failed to close server socket");
            System.exit(1);
        }
    }

    public static synchronized String getBoard(String data) {
        String[] board =  data.split(":");

        board[1] = board[1].replace('+',' ');
        boards.put(board[0],board[1]);

        return board[0];
    }

    public static synchronized String getBoardInfoforHTML(String boardId) {
        String boardF = boards.get(boardId);
        return boardF;
    }

    public static Board findBoardByCode(String boardCode) {
        boardRepository = PersistenceContext.repositories().BoardRepository();
        Board board = boardRepository.findByCode(Long.parseLong(boardCode)).get();
        return board;
    }

    static private String selectedBoardCode;

    public static void SelectedBoardtoShow(String boardCode) {
        selectedBoardCode = boardCode;
    }


    public static String getSelectedBoardCode() {
        return selectedBoardCode;
    }

    public static synchronized CreatePostItController createPostIt(){
        return thePostItController;
    }
    public static synchronized CreateBoardController createBoard() {
        return theBoardController;
    }

    public static synchronized ChangePostItController changePostIt() {
        return theChangePostItController;
    }

    public static synchronized UndoLastChangePostItController undoLastChangePostIt() {
        return theLatChangeController;
    }

    public static synchronized ShareBoardController shareBoard() {
        return theSharedController;
    }

    public static synchronized ArchiveBoardController archiveBoard() {
        return theArchiveController;
    }

}
