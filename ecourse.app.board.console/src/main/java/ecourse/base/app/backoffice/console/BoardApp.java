/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ecourse.base.app.backoffice.console;

import eapli.framework.io.util.Console;
import ecourse.base.MeetingManagement.event.InviteMeetingEvent;
import ecourse.base.MeetingManagement.event.InviteMeetingWatchDog;
import ecourse.base.app.backoffice.console.connection.util.Constants;
import ecourse.base.app.backoffice.console.presentation.MainMenu;
import ecourse.base.app.common.console.BaseApplication;
import ecourse.base.app.common.console.presentation.authz.LoginUI;
import ecourse.base.boardManagment.Event.BoardChangeWatchDog;
import ecourse.base.boardManagment.Event.BoardChangedEvent;
import ecourse.base.clientusermanagement.application.eventhandlers.NewUserRegisteredFromSignupWatchDog;
import ecourse.base.clientusermanagement.domain.events.NewUserRegisteredFromSignupEvent;
import ecourse.base.clientusermanagement.domain.events.SignupAcceptedEvent;
import ecourse.base.infrastructure.authz.AuthenticationCredentialHandler;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.application.eventhandlers.SignupAcceptedWatchDog;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public final class BoardApp extends BaseApplication {
    static private Socket socket;
    static private InetAddress serverIP;
    /**
     * avoid instantiation of this class.
     */
    private BoardApp() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        System.out.println("=====================================");
        System.out.println("Board App");
        System.out.println("(C) 2023");
        System.out.println("=====================================");

        AuthzRegistry.configure(PersistenceContext.repositories().users(),
                new BasePasswordPolicy(), new PlainTextEncoder());

        while (true) {
            String serverAddress = Console.readLine("Input Server address: (xxx.xxx.xxx.xxx)");

            try {
                serverIP = InetAddress.getByName(serverAddress);
                break;
            } catch (UnknownHostException ex) {
                System.out.println("Invalid SERVER-ADDRESS.");
            }
        }

        try {
            socket = new Socket(serverIP, Constants.SERVER_PORT_APP);
            System.out.println("CONECTEIIIIIIIIIIIIIIIIIIIIIIIIII");
        } catch (IOException ex) {
            System.out.println("Failed to connect to provided SERVER-ADDRESS and SERVER-PORT.");
            System.out.println("Application aborted.");
            System.exit(1);
        }

        new BoardApp().run(args);

    }

    @Override
    protected void doMain(final String[] args) {
        // login and go to main menu
        if (new LoginUI(new AuthenticationCredentialHandler()).show()) {
            // go to main menu
            final MainMenu menu = new MainMenu();
            menu.mainLoop();
        }
    }

    @Override
    protected String appTitle() {
        return "Board APP";
    }

    @Override
    protected String appGoodbye() {
        return "Board APP";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        dispatcher.subscribe(new NewUserRegisteredFromSignupWatchDog(),
                NewUserRegisteredFromSignupEvent.class);
        dispatcher.subscribe(new SignupAcceptedWatchDog(), SignupAcceptedEvent.class);
        dispatcher.subscribe(new InviteMeetingWatchDog(), InviteMeetingEvent.class);
        dispatcher.subscribe(new BoardChangeWatchDog(), BoardChangedEvent.class);
    }
}
