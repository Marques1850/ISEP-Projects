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
package ecourse.base.app.backoffice.console.presentation;

import ecourse.base.app.backoffice.console.presentation.UI.*;
import ecourse.base.Application;
import ecourse.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

import javax.swing.text.View;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;


    //BOARD MENU
    private static final int CREATE_BOARD_OPTION = 1;
    private static final int SHARE_BOARD_OPTION = 2;
    private static final int CREATE_POSTIT_OPTION = 3;
    private static final int CHANGE_POSTIT= 4;
    private static final int ARCHIVE_BOARD_OPTION = 5;
    private static final int UNDO_LAST_CHANGE_POSTIT_OPTION = 6;
    private static final int SEE_BOARD_OPTION = 7;
    private static final int VIEW_CHANGES_OPTION = 8;

    // MAIN MENU

    private static final int BOARD_OPTION = 1;


    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Base [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();



        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.MANAGER, BaseRoles.ADMIN, BaseRoles.TEACHER, BaseRoles.STUDENT)) {
            final Menu boardMenu = buildBoardMenu();
            mainMenu.addSubMenu(BOARD_OPTION, boardMenu);        //option 4
        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }




    private Menu buildBoardMenu() {
        final Menu menu = new Menu("Board >");

        menu.addItem(CREATE_BOARD_OPTION, "Create Board", new CreateBoardUI()::show);
        menu.addItem(SHARE_BOARD_OPTION, "Share a Board", new ShareBoardUI()::show);
        menu.addItem(CREATE_POSTIT_OPTION, "Create Post-It", new CreatePostItUI()::show);
        menu.addItem(CHANGE_POSTIT, "Change Post-It", new ChangePostItUI()::show);
        menu.addItem(ARCHIVE_BOARD_OPTION, "Archive Board", new ArchiveBoardUI()::show);
        menu.addItem(UNDO_LAST_CHANGE_POSTIT_OPTION, "Undo Last Change", new UndoLastChangePostItUI()::show);
        menu.addItem(SEE_BOARD_OPTION, "See Board", new SeeBoardUI()::show);
        menu.addItem(VIEW_CHANGES_OPTION, "View Board Changes", new ViewBoardChangesUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }





}
