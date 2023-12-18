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
package ecourse.base.app.backoffice.console.presentation.UI;

import java.util.*;

import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.usermanagement.UserRegistration.RegisterNewUserController;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.usermanagement.UserRegistration.RoleDto;

/**
 * UI for adding a user to the application.
 *
 * Created by Sérgio Marques on 01/05/23.
 */
public class RegisterNewUserUI extends AbstractUI {


    Scanner sc = new Scanner(System.in);

    private final RegisterNewUserController theController = new RegisterNewUserController();

    @Override
    protected boolean doShow() {
        // FIXME avoid duplication with SignUpUI. reuse UserDataWidget from

        final List<RoleDto> roleTypes = new ArrayList<>();

        boolean show;
        do {
            show = showRoles(roleTypes);
        } while (!show);

        final String firstName = Console.readLine("First name");
        final String lastName = Console.readLine("Last name");
        final String shortName = Console.readLine("Short name");
        final String email = Console.readLine("E-Mail");
        final String nif = Console.readLine("NIF");
        final String birthDate = Console.readLine("Birth date  (DD/MM/YYYY)");


        final String password = Console.readLine("Password\n");

        eCourseSystemUserDto st = new eCourseSystemUserDto();

        if(roleTypes.get(0).getRoleName().equals("STUDENT")){

            final String mecanographicNumber = Console.readLine("Mecanographic number");

            try{
                st = this.theController.createStudent(firstName, lastName, shortName, email, password, mecanographicNumber, nif, birthDate);
            } catch (final IntegrityViolationException | ConcurrencyException e) {
                System.out.println("That mecanographic number is already in use.");
            }

            st.toString();

        } else if (roleTypes.get(0).getRoleName().equals("TEACHER")) {

            final String acronym = Console.readLine("Acronym");

            try {
                st = this.theController.createTeacher(firstName, lastName, shortName, email, password, acronym, nif, birthDate);
            } catch (final IntegrityViolationException | ConcurrencyException e) {
                System.out.println("That mecanographic number is already in use.");
            }

            st.toString();

        } else if (roleTypes.get(0).getRoleName().equals("MANAGER")) {

            try {
                st = this.theController.createManager(firstName, lastName, shortName, email, password, nif, birthDate);
            } catch (final IntegrityViolationException | ConcurrencyException e) {
                System.out.println("That mecanographic number is already in use.");
            }

            st.toString();

        }

        System.out.println("Do ou wnat to add this user?\n");
        System.out.println("(y -> yes     n -> no\n)");

        String conf = sc.nextLine();

        if(conf.equals("y")){
            if(theController.addeUser(password)==true){
                System.out.println("User added successfully");
                return true;
            } else {
                System.out.println("User not added");
                return false;
            }
        } else if(conf.equals("n")){
            System.out.println("User not added");
            return false;
        }

        return false;
    }

    private boolean showRoles(final List<RoleDto> roleTypes) {
        // TODO we could also use the "widget" classes from the framework...
        final Menu rolesMenu = buildRolesMenu(roleTypes);
        final MenuRenderer renderer = new VerticalMenuRenderer(rolesMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildRolesMenu(final List<RoleDto> roleTypes) {
        final Menu rolesMenu = new Menu();
        int counter = 0;
        rolesMenu.addItem(MenuItem.of(counter++, "No Role", Actions.SUCCESS));
        for (final RoleDto roleType : theController.getRoleTypes()) {
            rolesMenu.addItem(MenuItem.of(counter++, roleType.toString(), () -> roleTypes.add(roleType)));
        }
        return rolesMenu;
    }

    @Override
    public String headline() {
        return "Register New User";
    }

    public RegisterNewUserController getController() {
        return this.theController;
    }

}
