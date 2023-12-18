package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.courseManagement.SetTeacherCourseController;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.usermanagement.domain.Course.CourseDto;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetTeacherCourseUI extends AbstractUI {

    private final SetTeacherCourseController theController = new SetTeacherCourseController();

    @Override
    protected boolean doShow() {

       final List<CourseDto> Courses =theController.getCourses();


      showCourses(Courses);

        String  courseCode;
        courseCode=Courses.get(Courses.size()-1).getCode();

       List<eCourseSystemUserDto> Teachers=theController.getTeachers();
        List<Acronym> acronyms = new ArrayList<>();
       String option;
       do {
           showTeachers(Teachers);
           String teacherAcronym=Teachers.get(Teachers.size() - 1).getAcronym();
           Acronym acronym=Acronym.valueOf(teacherAcronym) ;
           acronyms.add(acronym);
           Teachers.removeIf(t -> Objects.equals(t.getAcronym(), teacherAcronym));
           do {
               option = Console.readLine(" Do you want to add more teachers (y/n)");
           }while(!option.equals("y") && !option.equals("n"));
       }while(option.equals("y") && !Teachers.isEmpty());
        theController.setTeachersCourse(courseCode,acronyms);
        return false;
    }

    private boolean showCourses(final List<CourseDto> courses) {
        final Menu CourseMenu = buildCourseMenu(courses);
        final VerticalMenuRenderer renderer = new VerticalMenuRenderer(CourseMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }
    private boolean showTeachers(final List<eCourseSystemUserDto> users) {
        final Menu CourseMenu = buildTeacherMenu(users);
        final VerticalMenuRenderer renderer = new VerticalMenuRenderer(CourseMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildTeacherMenu(final List<eCourseSystemUserDto> teachers) {
        final Menu TeacherMenu = new Menu();
        int counter = 0;
        for ( eCourseSystemUserDto teacher : teachers  ) {
            TeacherMenu.addItem(MenuItem.of(counter++, teacher.toString(), () -> teachers.add(teacher)));
        }
        return TeacherMenu;
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
        return "Set Teachers in Course ";
    }

}

