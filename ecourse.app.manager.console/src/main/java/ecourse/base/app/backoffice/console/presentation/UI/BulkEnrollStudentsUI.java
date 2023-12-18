package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.EnrollmentManagment.BulkEnrollController;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.usermanagement.domain.Course.CourseDto;

import java.io.FileNotFoundException;
import java.util.*;

public class BulkEnrollStudentsUI extends AbstractUI{
    private final BulkEnrollController theController = new BulkEnrollController();

    @Override
    protected boolean doShow(){
        final List<CourseDto> Courses = theController.listCoursesEnrollmentOpen();

        boolean show;
        do{
            show = showCourses(Courses);
        }while(!show);

        String selectedCourse = Console.readLine("Select the ID of the course to enroll students: ");
        String csvPath= Console.readLine("Insert the path to the CSV file: ");

        List<eCourseSystemUserDto> enrolleds= null;
        try {
            enrolleds = theController.enrollStudents(selectedCourse, csvPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        showStudents(enrolleds);


        System.out.println("Students enrolled successfully");

        return true;
    }

    @Override
    public String headline() {
        return "Enroll Students";
    }

    private boolean showCourses(final List<CourseDto> courses) {
        final Menu CourseMenu = buildCourseMenu(courses);
        final MenuRenderer renderer = new VerticalMenuRenderer(CourseMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private boolean showStudents(final List<eCourseSystemUserDto> students) {
        final Menu StudentMenu = buildStudentMenu(students);
        final MenuRenderer renderer = new VerticalMenuRenderer(StudentMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildStudentMenu(final List<eCourseSystemUserDto> students) {
        final Menu StudentMenu = new Menu();
        int counter = 0;
        for (final eCourseSystemUserDto student : students  ) {
            StudentMenu.addItem(MenuItem.of(counter++, student.toString(), () -> students.add(student)));
        }
        return StudentMenu;
    }

    private Menu buildCourseMenu(final List<CourseDto> Courses) {
        final Menu CourseMenu = new Menu();
        int counter = 0;
        for (final CourseDto Course : Courses  ) {
            CourseMenu.addItem(MenuItem.of(counter++, Course.toString(), () -> Courses.add(Course)));
        }
        return CourseMenu;
    }

}


