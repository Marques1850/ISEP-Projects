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

import ecourse.base.MeetingManagement.presentation.CancelMeetingUI;
import ecourse.base.MeetingManagement.InvitationManagement.presentation.ListMeetingParticipantsUI;
import ecourse.base.MeetingManagement.InvitationManagement.presentation.ManageMeetingInviteUI;
import ecourse.base.courseManagement.presentation.ListCoursesUI;
import ecourse.base.MeetingManagement.presentation.ScheduleMeetingUI;
import ecourse.base.app.backoffice.console.presentation.UI.*;
import ecourse.base.app.common.console.presentation.authz.MyUserMenu;
import ecourse.base.Application;
import ecourse.base.app.backoffice.console.presentation.authz.DeactivateUserAction;
import ecourse.base.app.backoffice.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
import ecourse.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.ShowMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int ALTER_USER_STATUS_OPTION = 2;
    private static final int LIST_USERS_OPTION = 3;
    private static final int DEACTIVATE_USER_OPTION = 4;
    private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 5;

    // SETTINGS
    private static final int SET_KITCHEN_ALERT_LIMIT_OPTION = 1;

    // COURSES
    private static final int COURSE_REGISTER_OPTION = 1;
    private static final int COURSE_LIST_OPTION = 2;
    private static final int COURSES_OPEN_OPTION = 3;
    private static final int COURSES_CLOSE_OPTION = 4;
    private static final int COURSE_ENROLL_OPTION = 5;
    private static final int COURSE_SET_TEACHERS_OPTION = 6;

    // MEETINGS
    private static final int SCHEDULE_MEETING_OPTION = 1;
    private static final int CANCEL_MEETING_OPTION = 2;
    private static final int ACCEPT_REJECT_MEETING_OPTION = 3;
    private static final int LIST_PARTICIPANTS_OF_MEETINGS_OPTION = 4;


    //Enrollments

    private static final int BULK_ENROLL_OPTION= 1;
    private static final int MANAGE_ENROLL_OPTION = 2;

    //BOARD MENU
    private static final int CREATE_BOARD_OPTION = 1;
    private static final int SHARE_BOARD_OPTION = 2;
    private static final int CREATE_POSTIT_OPTION = 3;
    private static final int CHANGE_POSTIT= 4;
    private static final int ARCHIVE_BOARD_OPTION = 5;

    // DISHES
    private static final int DISH_REGISTER_OPTION = 5;
    private static final int DISH_LIST_OPTION = 6;
    private static final int DISH_REGISTER_DTO_OPTION = 7;
    private static final int DISH_LIST_DTO_OPTION = 8;
    private static final int DISH_ACTIVATE_DEACTIVATE_OPTION = 9;
    private static final int DISH_CHANGE_OPTION = 10;

    // DISH PROPERTIES
    private static final int CHANGE_DISH_NUTRICIONAL_INFO_OPTION = 1;
    private static final int CHANGE_DISH_PRICE_OPTION = 2;

    // MATERIALS
    private static final int MATERIAL_REGISTER_OPTION = 1;
    private static final int MATERIAL_LIST_OPTION = 2;

    // REPORTING
    private static final int REPORTING_DISHES_PER_DISHTYPE_OPTION = 1;
    private static final int REPORTING_HIGH_CALORIES_DISHES_OPTION = 2;
    private static final int REPORTING_DISHES_PER_CALORIC_CATEGORY_OPTION = 3;

    // MEALS
    private static final int LIST_MEALS_OPTION = 1;
    private static final int MEAL_REGISTER_OPTION = 2;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;

    private static final int COURSES_OPTION = 3;
    private static final int SETTINGS_OPTION = 4;
    private static final int MEETINGS_OPTION = 5;

    private static final int ENROLLMENTS_OPTION= 6;
    private static final int BOARD_OPTION = 7;
    private static final int TRACEABILITY_OPTION = 6;
    private static final int MEALS_OPTION = 7;
    private static final int REPORTING_DISHES_OPTION = 8;

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

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.MANAGER, BaseRoles.ADMIN)) {
            final Menu myUserMenu = new MyUserMenu();
            mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);    //option 1
            final Menu usersMenu = buildUsersMenu();
            mainMenu.addSubMenu(USERS_OPTION, usersMenu);       //option 2
            final Menu coursesMenu = buildCoursesMenu();
            mainMenu.addSubMenu(COURSES_OPTION, coursesMenu);   //option 3
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu); //option 4
            final Menu meetingsMenu = buildMeetingsMenu();
            mainMenu.addSubMenu(MEETINGS_OPTION, meetingsMenu); //option 5
            final Menu bulkEnrollMenu = buildEnrollMenu();
            mainMenu.addSubMenu(ENROLLMENTS_OPTION,bulkEnrollMenu);
        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }

    private Menu buildMeetingsMenu() {
        final Menu menu = new Menu("Meetings >");

        menu.addItem(SCHEDULE_MEETING_OPTION, "Schedule Meeting", new ScheduleMeetingUI()::show);
        menu.addItem(CANCEL_MEETING_OPTION, "Cancel Meeting", new CancelMeetingUI()::show);
        menu.addItem(ACCEPT_REJECT_MEETING_OPTION, "Accept/Reject Meeting", new ManageMeetingInviteUI()::show);
        menu.addItem(LIST_PARTICIPANTS_OF_MEETINGS_OPTION, "List Meeting Participants", new ListMeetingParticipantsUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildAdminSettingsMenu() {
        final Menu menu = new Menu("Settings >");

        menu.addItem(SET_KITCHEN_ALERT_LIMIT_OPTION, "Set kitchen alert limit",
                new ShowMessageAction("Not implemented yet"));
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users >");

        menu.addItem(ADD_USER_OPTION, "Register New User", new RegisterNewUserUI()::show);
        menu.addItem(ALTER_USER_STATUS_OPTION, "Alter User Status", new AlterUserStatusUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListSytemUsersUI()::show);
        menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
                new AcceptRefuseSignupRequestAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildCoursesMenu() {
        final Menu menu = new Menu("Courses >");

        menu.addItem(COURSE_REGISTER_OPTION, "Create Course", new CreateNewCourseUI()::show);
        menu.addItem(COURSE_LIST_OPTION, "List all Courses", new ListCoursesUI()::show);
        menu.addItem(COURSES_OPEN_OPTION, "Open Courses", new OpenCourseUI()::show);
        menu.addItem(COURSES_CLOSE_OPTION, "Close Courses", new CloseCourseUI()::show);
        menu.addItem(COURSE_ENROLL_OPTION, "Course Enrollments", new OpenCloseEnrollmentUI()::show);
        menu.addItem(COURSE_SET_TEACHERS_OPTION, "Set Teachers in course", new SetTeacherCourseUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildEnrollMenu(){
        final Menu menu = new Menu("Enrollments >");

        menu.addItem(BULK_ENROLL_OPTION,"Bulk Enroll Students", new BulkEnrollStudentsUI()::show);
        menu.addItem(MANAGE_ENROLL_OPTION, "Manage Enrollments", new ManageEnrollmentUI()::show);
        menu.addItem(EXIT_OPTION,RETURN_LABEL,Actions.SUCCESS);
        return menu;
    }





}
