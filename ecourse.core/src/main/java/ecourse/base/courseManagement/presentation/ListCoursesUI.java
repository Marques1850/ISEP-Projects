package ecourse.base.courseManagement.presentation;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.courseManagement.ListCoursesController;
import ecourse.base.usermanagement.domain.Course.Course;

import java.util.List;

public class ListCoursesUI extends AbstractUI {

    private ListCoursesController controller = new ListCoursesController();

    @Override
    protected boolean doShow() {
        try {
            List<Course> listCourses = controller.listCoursesAvailable();
            presentCourses(listCourses);

        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("!error!");
        }
        return false;
    }

    @Override
    public String headline() {
        return "List Courses";
    }

    private void presentCourses(List<Course> listCourses) {
        if ( listCourses.isEmpty() ) {
            System.out.println("No courses available");
        } else {
            System.out.println("Courses:");
            System.out.println("====================================");
            for (Course course : listCourses) {
                System.out.printf("ID:%s |\t Name: %s\n", course.identity(), course.name());
            }
            System.out.println("====================================");
        }
    }

}
