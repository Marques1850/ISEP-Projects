package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.courseManagement.OpenCloseEnrollmentController;
import ecourse.base.usermanagement.domain.Course.CourseDto;

import java.util.Scanner;

public class OpenCloseEnrollmentUI extends AbstractUI {

    Scanner sc = new Scanner(System.in);

    private final OpenCloseEnrollmentController theController = new OpenCloseEnrollmentController();

    private CourseDto courseenrol;

    @Override
    protected boolean doShow() {
        // FIXME avoid duplication with SignUpUI. reuse UserDataWidget from

        final String id = Console.readLine("Insert course code\n");

        boolean flag = theController.verifyCourseExists(id);

        if(flag == true){
            if(theController.VerifyCourseIsActive(id) != null){
                System.out.println("Course is open!\n");
                CourseDto course = theController.VerifyCourseIsActive(id);
                System.out.println(course.toString() + "\n");
                System.out.println("Do you want to enable enrolements for this course?\n");
                System.out.println("(y -> yes     n -> no\n)");
                String confirmation = sc.nextLine();
                if(confirmation.equals("y")){
                    this.courseenrol = theController.enableenrollments(id);
                    String status = courseenrol.getStatus();
                    if(!(status.equals(course.getStatus()))){
                        System.out.println("Course status altered to:" + status + "\n");
                    } else {
                        System.out.println("Course status not altered!\n");
                        return false;
                    }
                } else {
                    return false;
                }

            } else if(theController.VerifyCourseIsActiveAndEnrollment(id) != null){
                System.out.println("Course is acepting enrollments!\n");
                CourseDto course = theController.VerifyCourseIsActiveAndEnrollment(id);
                System.out.println(course.toString() + "\n");
                System.out.println("Do you want to disable enrolements for this course?\n");
                System.out.println("(y -> yes     n -> no\n)");
                String confirmation = sc.nextLine();
                if(confirmation.equals("y")){
                    this.courseenrol = theController.disableenrollments(id);
                    String status = courseenrol.getStatus();
                    if(!(status.equals(course.getStatus()))){
                        System.out.println("Course status altered to:" + status + "\n");
                    } else {
                        System.out.println("Course status not altered!\n");
                        return false;
                    }
                } else {
                    return false;
                }

            } else {
                System.out.println("Course is closed!\n");
                return false;
            }

        } else {
            System.out.println("Course don´t exist!\n");
            return false;
        }



        System.out.println("Do ou want to save the alteraion?\n");
        System.out.println("(y -> yes     n -> no\n)");

        String conf = sc.nextLine();

        if(conf.equals("y")){
            theController.saveSatusChange(this.courseenrol);
            return true;
        } else {
            return false;
        }
    }



    @Override
    public String headline() {
        return "Alter Course Status";
    }
}
