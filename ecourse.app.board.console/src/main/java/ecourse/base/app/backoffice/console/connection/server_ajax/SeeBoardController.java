package ecourse.base.app.backoffice.console.connection.server_ajax;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.PostItMagnament.UserAccessBoardsService;
import ecourse.base.PostItMagnament.domain.PostIt;
import ecourse.base.app.backoffice.console.connection.SBPMessage;
import ecourse.base.app.backoffice.console.connection.util.Constants;
import ecourse.base.boardManagment.ShareBoardDto;
import ecourse.base.boardManagment.ShareBoardMapper;
import ecourse.base.boardManagment.domain.Board;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.aspectj.apache.bcel.classfile.Constant;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class SeeBoardController {
    private static BoardRepository theRepository = PersistenceContext.repositories().BoardRepository();
    private final eCourseUserRepository theUserRepository = PersistenceContext.repositories().eCourseUsers();
    private final ecourse.base.PostItMagnament.UserAccessBoardsService UserAccessBoardsService = new UserAccessBoardsService();

    static SeeBoardController instance;

    public static SeeBoardController getInstance() {
        if(instance == null){
            instance = new SeeBoardController();
        }
        return instance;
    }

    public List<ShareBoardDto> getUserBoards() {
        EmailAddress sessionEmail = AuthzRegistry.authorizationService().session().get().authenticatedUser().email();
        List<Board> boards = UserAccessBoardsService.findBoardsOfUser(sessionEmail, theRepository, theUserRepository);
        List<ShareBoardDto> shareBoardDtos = new ArrayList<>();
        for (Board board: boards) {
            shareBoardDtos.add(ShareBoardMapper.toDto(board));
        }
        return shareBoardDtos;
    }


    public void sendBoardInfo(String selectedBoard, String ip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String boardinfo;
                    theRepository = PersistenceContext.repositories().BoardRepository();
                    Board board = theRepository.findByCode(Long.parseLong(selectedBoard)).get();
                    StringBuilder sb = new StringBuilder();
                    sb.append(board.getId()); // Add the board code to the string
                    sb.append(":");
                    sb.append(board.boardName()); // Add the board name to the string
                    sb.append(";");
                    sb.append(board.getMaxRows());
                    sb.append(";");
                    sb.append(board.getMaxColumns());
                    sb.append(";");
                    for (Map.Entry<Pair<Integer, Integer>, PostIt> entry : board.getPostIts().entrySet()) {
                        sb.append(entry.getKey().a);
                        sb.append(",");
                        sb.append(entry.getKey().b);
                        sb.append(",");
                        sb.append(entry.getValue().getContent());
                        sb.append("|");
                    }
                    boardinfo = sb.toString();
                    try {
                        Socket socket = new Socket(ip, Constants.SERVER_PORT); // Replace with your server address and port
                        OutputStreamWriter dataWriterSv = new OutputStreamWriter(socket.getOutputStream());
                        dataWriterSv.write(SBPMessage.encodeBoardInfo(boardinfo));
                        dataWriterSv.flush();
                        socket.close();
                        sleep(2000);
                    } catch (IOException e) {
                        // Handle exception
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();
    }


}
