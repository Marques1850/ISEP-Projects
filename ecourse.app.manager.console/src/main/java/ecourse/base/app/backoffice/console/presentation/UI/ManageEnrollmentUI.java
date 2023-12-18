package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.EnrollmentManagment.ManageEnrollController;
import ecourse.base.EnrollmentManagment.domain.Enrollment;

import java.util.List;

public class ManageEnrollmentUI extends AbstractUI {
    @Override
    protected boolean doShow() {
        final ManageEnrollController controller = new ManageEnrollController();

        List<Enrollment> enrollments = controller.getOpenEnrollments();
        System.out.println(enrollments);
        if (enrollments.isEmpty()) {
            System.out.println("There are no enrollments to manage");
            return false;
        }
        for (Enrollment enrollment: enrollments) {
            try {
                System.out.println("Enrollment information: \n");
                System.out.println("Enrollment Course: " + enrollment.course().name());
                System.out.println("Enrollment Student Mecanographic Number: " + enrollment.studentID());
                System.out.println("Enrollment Status: " + enrollment.status());
                System.out.println("----------------------------------------------");
                String option = Console.readLine("Do you want to accept this enrollment? (Y/N)\n");
                if (option.equals("Y") || option.equals("y")) {
                    controller.acceptEnrollment(enrollment);
                } else if (option.equals("N") || option.equals("n")) {
                    controller.rejectEnrollment(enrollment);
                } else {
                    throw new IllegalArgumentException("Invalid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Successully managed enrollment!");
        }



        return false;
    }

    @Override
    public String headline() {
        return "Manage Enrollment";
    }
}
