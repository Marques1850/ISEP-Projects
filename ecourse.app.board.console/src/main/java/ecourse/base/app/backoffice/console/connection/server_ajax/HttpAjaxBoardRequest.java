package ecourse.base.app.backoffice.console.connection.server_ajax;

import ecourse.base.app.backoffice.console.connection.SharedBoardServer;
import java.io.*;
import java.net.Socket;

public class HttpAjaxBoardRequest extends Thread {
    String baseFolder;
    Socket sock;
    DataInputStream inS;
    DataOutputStream outS;

    public HttpAjaxBoardRequest(Socket s, String f) {
        baseFolder = f;
        sock = s;
    }

    public void run() {
        try {
            outS = new DataOutputStream(sock.getOutputStream());
            inS = new DataInputStream(sock.getInputStream());
        } catch (IOException ex) {
            System.out.println("Thread error on data streams creation");
        }
        try {
            HTTPmessage request = new HTTPmessage(inS);
            HTTPmessage response = new HTTPmessage();

            if (request.getMethod().equals("GET")) {
                if (request.getURI().startsWith("/board")) {
                    response.setContentFromString(
                            SharedBoardServer.getBoardInfoforHTML(request.getURI().substring(6)),
                            "text/html");
                    response.setResponseStatus("200 Ok");
                } else {
                    String fullname = baseFolder + "/";
                    if (request.getURI().equals("/" + request.getURI().charAt(1))) {
                        fullname = fullname + "Board.html";
                    } else {
                        fullname = fullname + request.getURI();
                    }
                    if (response.setContentFromFile(fullname)) {
                        response.setResponseStatus("200 Ok");
                    } else {
                        response.setContentFromString(
                                "<html><body><h1>404 File not found</h1></body></html>",
                                "text/html");
                        response.setResponseStatus("404 Not Found");
                    }
                }
                response.send(outS);
            } else { // NOT GET
                if (request.getMethod().equals("PUT") && request.getURI().startsWith("/")) {
                    response.setContentFromString(
                            SharedBoardServer.getBoard(request.getURI().substring(1)),
                            "text");
                    response.setResponseStatus("200 Ok");
                } else {
                    response.setContentFromString(
                            "<html><body><h1>ERROR: 405 Method Not Allowed</h1></body></html>",
                            "text/html");
                    response.setResponseStatus("405 Method Not Allowed");
                }
                response.send(outS);
            }
        } catch (IOException ex) {
            System.out.println("Thread error when reading request");
        }
        try {
            sock.close();
        } catch (IOException ex) {
            System.out.println("CLOSE IOException");
        }
    }
}

