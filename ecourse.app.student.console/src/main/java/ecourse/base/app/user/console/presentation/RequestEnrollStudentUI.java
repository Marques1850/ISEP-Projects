package ecourse.base.app.user.console.presentation;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

import ecourse.base.EnrollmentManagment.RequestEnrollStudentController;
import ecourse.base.usermanagement.domain.Course.CourseDto;

import java.util.List;

public class RequestEnrollStudentUI extends AbstractUI {
    private final RequestEnrollStudentController theController = new RequestEnrollStudentController();

    @Override
    protected boolean doShow(){
        final List<CourseDto> Courses = theController.listCoursesEnrollmentOpen();

        boolean show;
        do{
            show = showCourses(Courses);
        }while(!show);

        String selectedCourse = Console.readLine("Select the ID of the course to enroll students: ");


        theController.requestEnrollStudent(selectedCourse);

        System.out.println("Request sent successfully");

        return true;
    }

    @Override
    public String headline() {
        return "Request Enroll Student";}

    private boolean showCourses(final List<CourseDto> courses) {
        final Menu CourseMenu = buildCourseMenu(courses);
        final MenuRenderer renderer = new VerticalMenuRenderer(CourseMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
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
