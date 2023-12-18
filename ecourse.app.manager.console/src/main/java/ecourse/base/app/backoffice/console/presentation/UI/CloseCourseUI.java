package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.courseManagement.Controllers.CloseCourseController;
import ecourse.base.usermanagement.domain.Course.CourseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CloseCourseUI extends AbstractUI {


    private final CloseCourseController theController = new CloseCourseController();

    @Override
    protected boolean doShow() {

        final List<CourseDto> Courses =theController.getOpenCourses();


        List<String> selectedCourses = new ArrayList<>();
        String option;
        do {
            showCourses(Courses);
            String  Code=(Courses.get(Courses.size() - 1).code) ;

            selectedCourses.add(Code);
            Courses.removeIf(t -> Objects.equals(t.code, Code));
            do {
                option = Console.readLine(" Do you want to close more courses (y/n)");
            }while(!option.equals("y") && !option.equals("n"));
        }while(option.equals("y") && !Courses.isEmpty());
        theController.closingCourses(selectedCourses);

        System.out.println("Courses closed successfully");

     return false;
    }

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

    @Override
    public String headline() {
        return "Close Course ";
    }





}
