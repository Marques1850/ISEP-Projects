package ecourse.base.app.backoffice.console.connection;


public class SBPMessage {
    public static final char COMMTEST = '0';
    public static final char DISCONN = '1';
    public static final char ACK = '2';
    public static final char ERR = '3';
    public static final char AUTH = '4';
    public static final char BOARDINFO = '5';
    private static final char version = '1';

    public static char[] encodeBoardInfo(String boardInfo) {
        int dataLength = boardInfo.length();
        int D_LENGTH_2 = dataLength / 256;
        int D_LENGTH_1 = dataLength % 256;

        char[] msg = new char[4 + dataLength];
        msg[0] = SBPMessage.version;
        msg[1] = '5';
        msg[2] = (char) D_LENGTH_1;
        msg[3] = (char) D_LENGTH_2;
        for (int i = 0; i < dataLength; i++) {
            if ( boardInfo.charAt(i) == ' ' ) {
                msg[4 + i] = '+';
            }else {
                msg[4 + i] = boardInfo.charAt(i);
            }
        }
        return msg;
    }


    public static char[] encodeCommTest() {
        char msg[] = new char[4];
        msg[0] = SBPMessage.version;
        msg[1] = SBPMessage.COMMTEST;
        msg[2] = '0';
        msg[3] = '0';
        return msg;
    }

    public static char[] encodeDisconn() {
        char[] msg = new char[4];
        msg[0] = SBPMessage.version;
        msg[1] = SBPMessage.DISCONN;
        msg[2] = '0';
        msg[3] = '0';
        return msg;
    }

    public static char[] encodeAck() {
        char[] msg = new char[4];
        msg[0] = SBPMessage.version;
        msg[1] = SBPMessage.ACK;
        msg[2] = '0';
        msg[3] = '0';
        return msg;
    }

    public static char[] encodeErr() {
        char[] msg = new char[4];
        msg[0] = SBPMessage.version;
        msg[1] = SBPMessage.ERR;
        msg[2] = '0';
        msg[3] = '0';
        return msg;
    }

    public static char[] encodeErr(String errorMsg) {
        char[] errorMsgChar = errorMsg.toCharArray();
        return encodeErr(errorMsgChar);
    }

    public static char[] encodeErr(char[] errorMsgChar) {
        int dataLength = errorMsgChar.length;
        int D_LENGTH_2 = dataLength / 256;
        int D_LENGTH_1 = dataLength % 256;

        char[] msg = new char[4 + dataLength];
        msg[0] = SBPMessage.version;
        msg[1] = SBPMessage.ERR;
        msg[2] = (char) D_LENGTH_1;
        msg[3] = (char) D_LENGTH_2;
        for (int i = 0; i < dataLength; i++) {
            msg[4 + i] = errorMsgChar[i];
        }
        return msg;
    }

    public static char[] encodeAuth(String username, String password) {
        char[] usernameChar = username.toCharArray();
        char[] passwordChar = password.toCharArray();
        return encodeAuth(usernameChar, passwordChar);
    }

    public static char[] encodeAuth(char[] usernameChar, char[] passwordChar) {
        int dataLength = usernameChar.length + passwordChar.length + 2;
        int D_LENGTH_2 = dataLength / 256;
        int D_LENGTH_1 = dataLength % 256;
        char[] msg = new char[4 + dataLength];
        msg[0] = SBPMessage.version;
        msg[1] = SBPMessage.AUTH;
        msg[2] = (char) D_LENGTH_1;
        msg[3] = (char) D_LENGTH_2;
        int i;
        for (i = 0; i < usernameChar.length; i++) {
            msg[4 + i] = usernameChar[i];
        }
        msg[4 + i] = '\0';
        i++;
        for (int j = 0; j < passwordChar.length; j++) {
            msg[4 + i + j] = passwordChar[j];
        }
        msg[4 + i + passwordChar.length] = '\0';
        return msg;
    }


    public static char type(char[] messageFromClient) {
        return messageFromClient[1];
    }


    public static boolean isValidSBPMessage(char[] message) {
        if (message[0] != '1') {
            return false;
        }
        if (isCOMMTEST(message) || isDISCONN(message) || isACK(message)) {
            if (message[2] == '0' && message[3] == '0') {
                return true;
            }
            return false;
        }
        if (isERR(message) || isAUTH(message)) {
            int dataLength = calculateDataLength(message);
            if (dataLength == message.length - 4) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCOMMTEST(char[] response) {
        return response[1] == COMMTEST;
    }

    public static boolean isDISCONN(char[] response) {
        return response[1] == DISCONN;
    }

    public static boolean isACK(char[] response) {
        return response[1] == ACK;
    }

    public static boolean isERR(char[] response) {
        return response[1] == ERR;
    }

    public static boolean isAUTH(char[] response) {
        return response[1] == AUTH;
    }

    public static char[] usernameFromAUTH(char[] messageFromClient) {
        if (!isValidSBPMessage(messageFromClient) || !isAUTH(messageFromClient)) {
            return null;
        }
        int dataLength = calculateDataLength(messageFromClient);
        char[] usernameTmp = new char[dataLength];
        int i;
        for (i = 4; i < dataLength + 4; i++) {
            if (messageFromClient[i] == '\0') {
                break;
            }
            usernameTmp[i - 4] = messageFromClient[i];
        }
        char[] username = new char[i - 4];
        for (int j = 0; j < i - 4; j++) {
            username[j] = usernameTmp[j];
        }
        return username;
    }

    public static char[] passwordFromAUTH(char[] messageFromClient) {
        if (!isValidSBPMessage(messageFromClient) || !isAUTH(messageFromClient)) {
            return null;
        }
        int dataLength = calculateDataLength(messageFromClient);

        int i, j;
        for (i = 4; i < dataLength + 4; i++) {
            if (messageFromClient[i] == '\0') {
                break;
            }
        }
        i++;
        char[] password = new char[dataLength - i + 3];
        for (j = 0; j < dataLength + 4; j++) {
            if (messageFromClient[i + j] == '\0') {
                break;
            }
            password[j] = messageFromClient[i + j];
        }

        return password;
    }

    private static int calculateDataLength(char[] message) {
        int length1 = message[2];
        int length2 = message[3];
        return length1 + 256 * length2;
    }

}
