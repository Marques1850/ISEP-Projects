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

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.usermanagement.UserStatus.AlterUserStatusController;

import java.util.Scanner;

/**
 * UI for adding a user to the application.
 *
 * Created by Sérgio Marques on 02/05/23.
 */
public class AlterUserStatusUI extends AbstractUI {


    Scanner sc = new Scanner(System.in);

    private final AlterUserStatusController theController = new AlterUserStatusController();

    @Override
    protected boolean doShow() {
        // FIXME avoid duplication with SignUpUI. reuse UserDataWidget from

        final String id = Console.readLine("Insert user email\n");

        eCourseSystemUserDto user = theController.searchUser(id);

        if(user != null){
            System.out.println("User found!\n");
            System.out.println("User name: " + user.getName() + "\n");
            System.out.println("User email: " + user.getEmail() + "\n");
            System.out.println("User status: " + user.userStatus()+ "\n");
        } else {
            System.out.println("User not found!\n");
            return false;
        }

        eCourseSystemUserDto userBefore = user;



        System.out.println("Do ou want to alter the status of  this user?\n");
        System.out.println("(y -> yes     n -> no\n)");

        String conf = sc.nextLine();

        if(conf.equals("y")){
            String status = theController.alterUserStatus(user);
            if(!(status.equals(userBefore.userStatus()))){
                System.out.println("User status altered to:" + status + "\n");
                return true;
            } else {
                System.out.println("User status not altered!\n");
                return false;
            }
        } else {
            return false;
        }
    }



    @Override
    public String headline() {
        return "Alter User Status";
    }
}
