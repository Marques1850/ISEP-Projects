package ecourse.base.app.backoffice.console.connection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SBPMessageTest {
    @Test
    void testEncodeCommTest() {
        char[] expected = new char[]{'1','0','0','0'};
        char[] result = SBPMessage.encodeCommTest();
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void testEncodeDisconn() {
        char[] expected = "1100".toCharArray();
        char[] result = SBPMessage.encodeDisconn();
        assertArrayEquals(expected, result);
    }

    @Test
    void testEncodeAck() {
        char[] expected = "1200".toCharArray();
        char[] result = SBPMessage.encodeAck();
        assertArrayEquals(expected, result);
    }

    @Test
    void testEncodeErr() {
        char[] expected = "1300".toCharArray();
        char[] result = SBPMessage.encodeErr();
        assertArrayEquals(expected, result);
    }

    @Test
    void testEncodeErrWithMessage() {
        char[] msg = "Error message".toCharArray();
        int dataLength = msg.length;
        int length1 = dataLength % 256;
        int length2 = dataLength / 256;
        char[] expected = new char[4 + dataLength];
        expected[0] = '1';
        expected[1] = '3';
        expected[2] = (char)length1;
        expected[3] = (char)length2;
        for (int i = 0; i < dataLength; i++) {
            expected[4 + i] = msg[i];
        }
        assertArrayEquals(expected, SBPMessage.encodeErr(msg));
    }

    @Test
    void testEncodeAuth() {
        char[] username = "username".toCharArray();
        char[] password = "password".toCharArray();
        char[] msg = new char[username.length + password.length + 2];
        for (int i = 0; i < username.length; i++) {
            msg[i] = username[i];
        }
        msg[username.length] = '\0';
        for (int i = 0; i < password.length; i++) {
            msg[username.length + 1 + i] = password[i];
        }
        msg[username.length + 1 + password.length] = '\0';

        int dataLength = msg.length;
        int length1 = dataLength % 256;
        int length2 = dataLength / 256;
        char[] expected = new char[4 + dataLength];
        expected[0] = '1';
        expected[1] = '4';
        expected[2] = (char)length1;
        expected[3] = (char)length2;
        for (int i = 0; i < dataLength; i++) {
            expected[4 + i] = msg[i];
        }
        assertArrayEquals(expected, SBPMessage.encodeAuth(username, password));
    }

    @Test
    void testType() {
        char[] msgCOMMTEST = "1000".toCharArray();
        char[] msgDISCONN = "1100".toCharArray();
        char[] msgACK = "1200".toCharArray();
        char[] msgERR = "1300".toCharArray();
        char[] msgAUTH = "1400".toCharArray();

        assertEquals('0', SBPMessage.type(msgCOMMTEST));
        assertEquals('1', SBPMessage.type(msgDISCONN));
        assertEquals('2', SBPMessage.type(msgACK));
        assertEquals('3', SBPMessage.type(msgERR));
        assertEquals('4', SBPMessage.type(msgAUTH));
    }

    @Test
    void testIsValidSBPMessage() {
        char[] fail1 = "0000".toCharArray();
        char[] fail2 = "1001".toCharArray();
        char[] fail3 = "1210".toCharArray();

        assertFalse(SBPMessage.isValidSBPMessage(fail1));
        assertFalse(SBPMessage.isValidSBPMessage(fail2));
        assertFalse(SBPMessage.isValidSBPMessage(fail3));
    }

    @Test
    void testIsCOMMTEST() {
        char[] msgCOMMTEST = new char[4];
        msgCOMMTEST[0] = '1';
        msgCOMMTEST[1] = SBPMessage.COMMTEST;
        msgCOMMTEST[2] = '0';
        msgCOMMTEST[3] = '0';
        assertTrue(SBPMessage.isCOMMTEST(msgCOMMTEST));
    }

    @Test
    void testIsDISCONN() {
        char[] msgDISCONN = new char[4];
        msgDISCONN[0] = '1';
        msgDISCONN[1] = SBPMessage.DISCONN;
        msgDISCONN[2] = '0';
        msgDISCONN[3] = '0';
        assertTrue(SBPMessage.isDISCONN(msgDISCONN));
    }

    @Test
    void testIsACK() {
        char[] msgACK = new char[4];
        msgACK[0] = '1';
        msgACK[1] = SBPMessage.ACK;
        msgACK[2] = '0';
        msgACK[3] = '0';
        assertTrue(SBPMessage.isACK(msgACK));
    }

    @Test
    void testIsERR() {
        char[] msgERR = new char[4];
        msgERR[0] = '1';
        msgERR[1] = SBPMessage.ERR;
        msgERR[2] = '0';
        msgERR[3] = '0';
        assertTrue(SBPMessage.isERR(msgERR));
    }

    @Test
    void testIsAUTH() {
        char[] msgAUTH = new char[4];
        msgAUTH[0] = '1';
        msgAUTH[1] = SBPMessage.AUTH;
        msgAUTH[2] = '0';
        msgAUTH[3] = '0';
        assertTrue(SBPMessage.isAUTH(msgAUTH));
    }

    @Test
    void testUsernameFromAUTH() {
        char[] username = "username".toCharArray();
        char[] password = "password".toCharArray();
        char[] result = SBPMessage.encodeAuth(username, password);
        assertArrayEquals(username, SBPMessage.usernameFromAUTH(result));
    }

    @Test
    void testPasswordFromAUTH() {
        char[] username = "username".toCharArray();
        char[] password = "password".toCharArray();
        char[] result = SBPMessage.encodeAuth(username, password);
        assertArrayEquals(password, SBPMessage.passwordFromAUTH(result));
    }
}