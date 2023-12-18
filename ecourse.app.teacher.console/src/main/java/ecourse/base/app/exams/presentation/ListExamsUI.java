package ecourse.base.app.exams.presentation;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.app.exams.ListExamsController;
import ecourse.base.courseManagement.ListCoursesController;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.util.List;

public class ListExamsUI extends AbstractUI {

    private ListCoursesController controllerCourses = new ListCoursesController();
    private ListExamsController controllerExams = new ListExamsController();

    @Override
    protected boolean doShow() {
        try {
            List<Course> listCourses = controllerCourses.listCoursesAvailable();
            if ( listCourses == null || listCourses.isEmpty() ) {
                System.out.println("No courses available");
                return false;
            }
            presentCourses(listCourses);

            String courseID= Console.readLine("Insert the ID of the Course: ");
            while ( !CourseCode.isValidCode(courseID) ){
                System.out.println("Invalid Course ID");
                courseID= Console.readLine("Insert the ID of the Course: ");
            }

            List<Exam> listExams = controllerExams.listCourseExams( courseID );
            presentExams(listExams, courseID);

        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("!error!");
        }
        return false;
    }

    @Override
    public String headline() {
        return "List Exams Of Course";
    }

    private void presentCourses(List<Course> listCourses) {
        if ( listCourses == null || listCourses.isEmpty() ) {
            System.out.println("No courses available");
        } else {
            System.out.println("Courses:");
            System.out.println("====================================");
            for (Course course : listCourses) {
                System.out.printf("ID: %s |\t Name: %s\n", course.identity(), course.name());
            }
            System.out.println("====================================");
        }
    }

    private void presentExams(List<Exam> listExams, String courseID) {
        if ( listExams == null || listExams.isEmpty() ) {
            System.out.println(courseID + " doesn't have exams.");
        } else {
            System.out.println("Exams:");
            System.out.println("====================================");
            for (Exam exam : listExams) {
                System.out.printf("ID: %s |\t Name: %s\n", exam.identity(), exam.name());
            }
            System.out.println("====================================");
        }
    }

}
