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
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.application.CourseBuilder;
import ecourse.base.usermanagement.application.eventhandlers.CreateNewCourseController;
import ecourse.base.usermanagement.domain.Course.CourseDto;

import java.util.Scanner;

/**
 * UI for adding a user to the application.
 *
 * Created by Sérgio Marques on 01/05/23.
 */
public class CreateNewCourseUI extends AbstractUI {


    Scanner sc = new Scanner(System.in);

    private final CreateNewCourseController theController = new CreateNewCourseController();

    private final CourseBuilder courseBuilder = new CourseBuilder();

    @Override
    protected boolean doShow() {

        final String code = Console.readLine("Input the Course code\n");
        final String name = Console.readLine("Input the Course name\n");
        final String minStudents = Console.readLine("Input the minimum students to open the course\n");
        final String maxStudents = Console.readLine("Input the maximum students taht a course can have\n");
        final String description = Console.readLine("Input the Course description\n");

        boolean existsCourse = theController.verifyCourse(code);

        if(existsCourse){
            System.out.println("This course already exists\n");
            return false;
        } else {
            System.out.println("This course doesn't exist\n");
        }

        String regentTeacher = Console.readLine("Chose the Regent Teacher by typing the corresponding email\n");

        boolean existsTeacher = theController.existsTeacher(regentTeacher);

        if (existsTeacher){
            System.out.println("This teacher exists\n");
        } else {
            while(existsTeacher == false) {
                System.out.println("This teacher doesn't exist\n");
                regentTeacher = Console.readLine("Please choose another teacher\n");
                existsTeacher = theController.existsTeacher(regentTeacher);
            }
        }

        CourseDto dto = theController.createCourse(code, name, minStudents, maxStudents, description, regentTeacher);

        System.out.println("Do ou wnat to add this Course?\n");
        System.out.println(dto.toString());
        System.out.println("(y -> yes     n -> no\n)");

        String conf = sc.nextLine();

        if(conf.equals("y")){
            if(theController.addCourse()){
                System.out.println("Course added successfully");
                return true;
            } else {
                System.out.println("Course not added");
                return false;
            }
        } else if(conf.equals("n")){
            System.out.println("Course not added");
            return false;
        }

        return false;
    }

    @Override
    public String headline() {
        return "Create New Course";
    }




}
