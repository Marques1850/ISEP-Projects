package ecourse.base.app.UI;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.ExamMagnament.Controllers.CreateExamController;
import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class CreateExamUI extends AbstractUI {

    Scanner sc = new Scanner(System.in);

    private final CreateExamController theController = new CreateExamController();
    @Override
    protected boolean doShow() {

        final String examCode = Console.readLine("Enter the exam code:\n");
        final String title = Console.readLine("Enter the exam title:\n");
        final String courseCode = Console.readLine("Write the course of the exam\n");
        final String openDate = Console.readLine("Write the open date of the exam in the respective format -> DD/MM/YYYY\n");
        final String closeDate = Console.readLine("Write the close date of the exam in the respective format -> DD/MM/YYYY\n");
        final String fileName = Console.readLine("Please insert the exam file name:");

        ExamDto examDto;

        try {
            theController.verifyExam(fileName);
            examDto = theController.createExam(examCode, title, courseCode, openDate, closeDate, fileName);
            System.out.println("This is the exam you created: " + examDto.toString());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Exam created successfully!");


        return false;
    }

    @Override
    public String headline() {
        return "Create Exam";
    }
}
