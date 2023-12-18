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
import ecourse.base.app.UI.*;
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
import ecourse.base.app.exams.presentation.ListExamsUI;
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
    private static final int EXAMS_OPTION = 3;
    private static final int MEETINGS_OPTION = 4;
    private static final int CLASS_OPTION = 5;
    private static final int BOARD_OPTION = 6;

    // COURSES MENU
    private static final int LIST_COURSES_OPTION = 1;

    // EXAMS MENU
    private static final int CREAT_EXAM = 1;
    private static final int LIST_EXAMS_OPTION = 2;
    private static final int LIST_EXAMS_BY_COURSE_OPTION = 3;
    private static final int CREATE_QUESTION = 4;
    private static final int CREAT_FORMATIVE_EXAM = 5;

    // MEETINGS MENU
    private static final int SCHEDULE_MEETING = 1;
    private static final int CANCEL_MEETING_OPTION = 2;
    private static final int ACCEPT_REJECT_MEETING_OPTION = 3;
    private static final int LIST_PARTICIPANTS_OF_MEETINGS_OPTION = 4;

    // SETTINGS
    private static final int SET_USER_ALERT_LIMIT_OPTION = 1;

    //CLASS MENU
    private static final int SCHEDULE_CLASS_OPTION = 1;
    private static final int LIST_CLASS_OPTION = 2;
    private static final int SCHEDULE_EXTRA_CLASS_OPTION = 3;
    private static final int UPDATE_CLASS_SCHEDULE = 4;

    //BOARD MENU
    private static final int CREATE_BOARD_OPTION = 1;
    private static final int CREATE_POSTIT_OPTION = 2;
    private static final int ARCHIVE_BOARD_OPTION = 3;

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

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.TEACHER)) {
            final Menu myUserMenu = new MyUserMenu();
            mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);        //option 1
            final Menu coursesMenu = buildCoursesMenu();
            mainMenu.addSubMenu(COURSES_OPTION, coursesMenu);       //option 2
            final Menu examsMenu = buildExamsMenu();
            mainMenu.addSubMenu(EXAMS_OPTION, examsMenu);           //option 3
            final Menu meetingsMenu = buildMeetingsMenu();
            mainMenu.addSubMenu(MEETINGS_OPTION, meetingsMenu);     //option 4
            final Menu classMenu = scheduleClassMenu();
            mainMenu.addSubMenu(CLASS_OPTION, classMenu);        //option 5

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
        menu.addItem(EXIT_OPTION, RETURN, Actions.SUCCESS);

        return menu;
    }

    private Menu buildExamsMenu() {
        final Menu menu = new Menu("Exams >");
        menu.addItem(CREAT_EXAM, "Create Exam", new CreateExamUI()::show);
        menu.addItem(LIST_EXAMS_OPTION, "List exams", new ListExamsUI()::show);
        menu.addItem(LIST_EXAMS_BY_COURSE_OPTION, "List exams grades by course", new ListExamGradesCourseUI()::show);
        menu.addItem(CREATE_QUESTION, "Create Question", new CreateQuestionUI()::show);
        menu.addItem(CREAT_FORMATIVE_EXAM, "Create Formative Exam", new CreateFormativeExamUI()::show);
        menu.addItem(EXIT_OPTION, RETURN, Actions.SUCCESS);

        return menu;
    }

    private Menu scheduleClassMenu() {
        final Menu menu = new Menu("Class >");

        menu.addItem(SCHEDULE_CLASS_OPTION, "Schedule Class", new ScheduleClassUI()::show);
        menu.addItem(LIST_CLASS_OPTION, "Show Scheduled Classes", new ShowScheduledClassesUI()::show);
        menu.addItem(SCHEDULE_EXTRA_CLASS_OPTION, "Schedule Extra Class", new ScheduleExtraClassUI()::show);
        menu.addItem(UPDATE_CLASS_SCHEDULE, "Update Class Schedule", new UpdateScheduledClassesUI()::show);
        menu.addItem(EXIT_OPTION, RETURN, Actions.SUCCESS);


        return menu;
    }

}
