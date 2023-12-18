/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ecourse.base.infrastructure.bootstrapers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ecourse.base.ExamMagnament.Controllers.CreateExamGradeController;
import ecourse.base.ExamMagnament.Controllers.CreateNewExamController;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.usermanagement.application.eventhandlers.CreateNewCourseController;
import ecourse.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;
import ecourse.base.usermanagement.domain.Course.CourseCode;

/**
 * @author Paulo Gandra Sousa
 */
public class MasterUsersBootstrapper extends UsersBootstrapperBase implements Action {

    @Override
    public boolean execute() {
      //  registerAdmin("isep959", "Password1", "Mary", "Smith", "mary@smith.com");
        /* registerAdmin("admin", TestDataConstants.PASSWORD1, "Jane", "Doe Admin",
                "jane.doe@email.local"); */

        registerManager("Johnn", "Doen", "isep124", "jon123hgd@gmail.com", "Password22", "123456779", "01/01/1990");
        registerTeacher("Francisco", "Marques", "FMarques", "FMarques@gmail.com", "Password2", "FRM", "555555554", "01/01/1990");
        registerCourse("EAPLI","Eapli","FMarques@gmail.com","10","200","Eapli 2023");
        registerCourse("RCOMP","Rcomp","FMarques@gmail.com","20","100","Rcomp 2023");
        registerCourse("SCOMP","Scomp","FMarques@gmail.com","15","120","Scomp 2023");
        registerTeacher("Francisco", "Gouveia", "FGouveia", "FGouveia@gmail.com", "Password1", "FGA", "144444449", "01/01/1990");
        registerUser("Leonardo","Cringer","Leocringe","LeonardoCringe@gmail.com", "Password1","000000001","987654322","01/01/1990");
        try {
            registerExam("EAPLI1","EAPLI1","FIRST EXAM OF EAPLI","EAPLI","01/01/2023","01/02/2023");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            registerExam("RCOMP1","RCOMP1","FIRST EXAM OF RCOMP","RCOMP","01/01/2024","01/02/2024");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        registerExamGrade("EAPLI1","LeonardoCringe@gmail.com",Float.parseFloat("15.00"));
        registerExamGrade("EAPLI1","LeonardoCringe@gmail.com",Float.parseFloat("16.50"));

        //CourseRepository courseRepository = PersistenceContext.repositories().courses();
        //courseRepository.save(new Course("TestStudent",CourseCode.valueOf("TEST1")));
        //courseRepository.save(new Course("TestStudent",CourseCode.valueOf("TEST2")));

        return true;

    }

    /**
     *
     */
    private void registerAdmin(final String username, final String password, final String firstName,
            final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.ADMIN);

        registerUser(username, password, firstName, lastName, email, roles);
    }
    private void registerCourse(final String name, final String acronym, final String coordinator, final String minStudents,
                                final String maxStudents, final String description){


        CreateNewCourseController controller = new CreateNewCourseController();
        boolean existsCourse = controller.verifyCourse(name);
        if(!existsCourse){
            boolean existsTeacher = controller.existsTeacher(coordinator);
            if (existsTeacher){
                controller.createCourse(name, acronym, minStudents, maxStudents, description,coordinator);
                controller.addCourse();
            }
        }

    }
    private void registerExamGrade(final String examCode, final String studentNumber, final float grade){
        CreateExamGradeController controller = new CreateExamGradeController();
        controller.createExamGrade(examCode,studentNumber,grade);
    }
    private void registerExam(String code, String name, String description, String courseCode, String openDate, String closeDate) throws ParseException {
        CreateNewExamController controller = new CreateNewExamController();
        Date opendate=new SimpleDateFormat("dd/MM/yyyy").parse(openDate);
        Date closedate=new SimpleDateFormat("dd/MM/yyyy").parse(closeDate);
        controller.createExam(code,name,description,CourseCode.valueOf(courseCode),opendate,closedate);

    }

    private void registerManager(final String firstName, final String lastName, final String shortName,
                                final String email, final String password, final String nif,
                                final String birthDate) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.MANAGER);

        registereCourseManager(firstName, lastName, shortName, email, password, nif, birthDate);
    }

    private void registerUser(final String firstName, final String lastName, final String shortName,
                              final String email, final String password, final String mecanoNumber,final String nif,
                              final String birthDate){
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.STUDENT);

        registereCourseStudent(firstName, lastName, shortName, email, password,mecanoNumber, nif, birthDate, roles);
    }

    private void registerTeacher(final String firstName, final String lastName, final String shortName, final String email,
                                 final String password, final String acronym, final String nif,
                                 final String birthDate){
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.TEACHER);

        registereCourseTeacher(firstName, lastName, shortName, email, password,acronym, nif, birthDate);
    }
}
