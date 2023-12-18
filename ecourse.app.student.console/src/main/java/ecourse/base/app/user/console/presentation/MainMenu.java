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
package ecourse.base.app.user.console.presentation;

import eapli.framework.actions.Actions;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import ecourse.base.Application;
import ecourse.base.MeetingManagement.presentation.CancelMeetingUI;
import ecourse.base.MeetingManagement.InvitationManagement.presentation.ListMeetingParticipantsUI;
import ecourse.base.MeetingManagement.InvitationManagement.presentation.ManageMeetingInviteUI;
import ecourse.base.courseManagement.presentation.ListCoursesUI;
import ecourse.base.MeetingManagement.presentation.ScheduleMeetingUI;
import ecourse.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.usermanagement.domain.BaseRoles;

/**
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends ClientUserBaseUI {

    private static final String SEPARATOR_LABEL = "--------------";

    private static final String RETURN = "Return ";

    private static final String NOT_IMPLEMENTED_YET = "Not implemented yet";

    private static final int EXIT_OPTION = 0;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int COURSES_OPTION = 2;
    private static final int MEETINGS_OPTION = 3;
    private static final int EXAMS_OPTION = 4;
    private static final int BOARD_OPTION = 5;

    // COURSES MENU
    private static final int LIST_COURSES_OPTION = 1;

    private static final int ENROLL_COURSE_OPTION = 2;

    // MEETINGS MENU
    private static final int SCHEDULE_MEETING = 1;
    private static final int CANCEL_MEETING_OPTION = 2;
    private static final int ACCEPT_REJECT_MEETING_OPTION = 3;
    private static final int LIST_PARTICIPANTS_OF_MEETINGS_OPTION = 4;

    // EXAMS MENU
    private static final int LIST_FUTURE_EXAMS_OPTION = 1;
    private static final int LIST_TAKE_EXAMS_OPTION = 2;
    private static final int LIST_GRADES_OPTION =3;
    private static final int LIST_TAKE_FORMATIVE_EXAMS_OPTION = 4;

    // BOARD MENU
    private static final int CREATE_BOARD_OPTION = 1;
    private static final int SHARE_BOARD_OPTION = 2;
    private static final int CREATE_POSTIT_OPTION = 3;
    private static final int ARCHIVE_BOARD_OPTION = 4;
    private static final int CHANGE_POSTIT_OPTION = 5;

    // SETTINGS
    private static final int SET_USER_ALERT_LIMIT_OPTION = 1;

    private final AuthorizationService authz =
            AuthzRegistry.authorizationService();

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

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.STUDENT)) {
            final Menu myUserMenu = new MyUserMenu();
            mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);        //option 1
            final Menu coursesMenu = buildCoursesMenu();
            mainMenu.addSubMenu(COURSES_OPTION, coursesMenu);       //option 2
            final Menu meetingsMenu = buildMeetingsMenu();
            mainMenu.addSubMenu(MEETINGS_OPTION, meetingsMenu);     //option 3
            final Menu ExamMenu = buildExamMenu();
            mainMenu.addSubMenu(EXAMS_OPTION, ExamMenu);     //option 4

        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }
    private Menu buildMeetingsMenu() {
        final Menu menu = new Menu("Meetings >");

        menu.addItem(SCHEDULE_MEETING, "Schedule Meeting", new ScheduleMeetingUI()::show);
        menu.addItem(CANCEL_MEETING_OPTION, "Cancel Meeting", new CancelMeetingUI()::show);
        menu.addItem(ACCEPT_REJECT_MEETING_OPTION, "Accept/Reject Meeting", new ManageMeetingInviteUI()::show);
        menu.addItem(LIST_PARTICIPANTS_OF_MEETINGS_OPTION, "List Meeting Participants", new ListMeetingParticipantsUI()::show);
        menu.addItem(EXIT_OPTION, RETURN, Actions.SUCCESS);

        return menu;
    }

    private Menu buildCoursesMenu() {
        final Menu menu = new Menu("Courses >");

        menu.addItem(LIST_COURSES_OPTION, "List courses", new ListCoursesUI()::show);
        menu.addItem(ENROLL_COURSE_OPTION, "Enroll in course", new RequestEnrollStudentUI()::show);
        menu.addItem(EXIT_OPTION, RETURN, Actions.SUCCESS);

        return menu;
    }

    private Menu buildExamMenu(){
        final Menu menu = new Menu("Exam >");

        menu.addItem(LIST_FUTURE_EXAMS_OPTION, "List future exams",new ListFutureExamsUI()::show);
        menu.addItem(LIST_TAKE_EXAMS_OPTION, "Take exam",new TakeExamUI()::show);
        menu.addItem(LIST_GRADES_OPTION, "List Grades",new ListGradesStudentsUI()::show);
        menu.addItem(LIST_TAKE_FORMATIVE_EXAMS_OPTION, "Take formative exam",new TakeFormativeExamUI()::show);
        menu.addItem(EXIT_OPTION, RETURN, Actions.SUCCESS);

        return menu;

    }
}
