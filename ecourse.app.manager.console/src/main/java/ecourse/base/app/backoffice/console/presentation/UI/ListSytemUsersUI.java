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

import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;

import ecourse.base.usermanagement.ListUsers.ListSystemUsersController;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Sérgio Marques
 */
@SuppressWarnings({ "squid:S106" })
public class ListSytemUsersUI extends AbstractUI {

    Scanner sc = new Scanner(System.in);
    private ListSystemUsersController theController = new ListSystemUsersController();

    @Override
    protected boolean doShow() {
        int numberOfUsers = theController.totalNumberOfUsers();

        System.out.println("Number of users: " + numberOfUsers);

        System.out.println("Do ou want see all users on the system?\n");
        System.out.println("(y -> yes     n -> no\n)");

        String conf = sc.nextLine();

        if(conf.equals("y")){
            List<eCourseSystemUserDto> list = theController.listSystemUsers();
            if(list == null){
                System.out.println("Don´t exist users.");
            }
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + " - " + list.get(i).toString());
            }
            System.out.println("List users was successful");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String headline() {
        return "List Users";
    }

}
