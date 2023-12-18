package ecourse.base.app.backoffice.console.connection;

import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import ecourse.base.app.backoffice.console.connection.server_ajax.HTTPmessage;
import ecourse.base.app.backoffice.console.connection.util.Constants;
import ecourse.base.usermanagement.domain.BaseRoles;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class ServerClientHandler implements Runnable {
    private final AuthenticationService authService = AuthzRegistry.authenticationService();
    private Socket socket;
    private OutputStreamWriter dataWriter;
    private InputStreamReader dataReader;
    private char[] clientName;
    private char[] password;

    public ServerClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.dataWriter = new OutputStreamWriter(socket.getOutputStream());
            this.dataReader = new InputStreamReader(socket.getInputStream());
        } catch (IOException e) {
            closeEverything(socket, dataWriter, dataReader);
        }
    }

    @Override
    public void run() {
        char[] messageFromClient, buffer;
        int bytesRead;
        System.out.println("SERVER: Client connected");
        while (socket.isConnected()) {
            try {
                buffer = new char[1024];
                bytesRead = dataReader.read(buffer);
                messageFromClient = new char[bytesRead];
                for (int i = 0; i < bytesRead; i++) {
                    messageFromClient[i] = buffer[i];
                }

                if (messageFromClient != null) {
                    System.out.println(" -! New message received: " + messageFromClient);
                    switch (SBPMessage.type(messageFromClient)) {
                        case SBPMessage.COMMTEST:
                        case SBPMessage.ACK:
                        case SBPMessage.ERR:
                            if (SBPMessage.isValidSBPMessage(messageFromClient)) {
                                dataWriter.write(SBPMessage.encodeAck());
                                dataWriter.flush();
                            } else {
                                System.out.println("Invalid message format");
                                dataWriter.write(SBPMessage.encodeErr("Invalid message format"));
                                dataWriter.flush();
                            }
                            break;
                        case SBPMessage.DISCONN:
                            dataWriter.write(SBPMessage.encodeAck());
                            dataWriter.flush();
                            socket.close();
                            break;
                        case SBPMessage.AUTH:
                            if (validateAuthentication(messageFromClient)) {
                                System.out.println("SERVER: Client (" + clientName + ") has entered the shared board chat.");
                                dataWriter.write(SBPMessage.encodeAck());
                                dataWriter.flush();
                            } else {
                                dataWriter.write(SBPMessage.encodeErr("Invalid username or password"));
                                dataWriter.flush();
                            }
                            break;
                        case SBPMessage.BOARDINFO:
                            HTTPmessage request = new HTTPmessage();
                            request.setRequestMethod("PUT");
                            request.setURI("/" + String.valueOf(messageFromClient).substring(4));
                            try {
                                Socket sock = new Socket( Constants.SERVER_HOSTNAME, Constants.AJAX_SERVER_PORT );
                                DataOutputStream sOut = new DataOutputStream(sock.getOutputStream());
                                request.send(sOut);
                                sock.close();
                            } catch (IOException ex) {
                                System.out.println("Error sending board code to server.");
                            }
                            dataWriter.write(SBPMessage.encodeAck());
                            dataWriter.flush();
                            break;
                    }
                }
            } catch (IOException | NegativeArraySizeException e1) {
                closeEverything(socket, dataWriter, dataReader);
                break;
            }
        }
    }

    private boolean validateAuthentication(char[] messageFromClient) {
        this.clientName = SBPMessage.usernameFromAUTH(messageFromClient);
        this.password = SBPMessage.passwordFromAUTH(messageFromClient);
        String username = String.valueOf(clientName);
        String pass = String.valueOf(this.password);
        Optional<UserSession> session = authService.authenticate(
                username,
                pass,
                BaseRoles.nonUserValues());
        if (session.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    private void removeServerClientHandler() {
        if (clientName != null) {
            System.out.println("SERVER: " + String.valueOf(clientName) + " has left.");
        } else {
            System.out.println("SERVER: Not authenticated client has left.");
        }
    }

    private void closeEverything(Socket socket, OutputStreamWriter dataWriter, InputStreamReader dataReader) {
        removeServerClientHandler();
        try {
            if (socket != null) socket.close();
            if (dataWriter != null) dataWriter.close();
            if (dataReader != null) dataReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
