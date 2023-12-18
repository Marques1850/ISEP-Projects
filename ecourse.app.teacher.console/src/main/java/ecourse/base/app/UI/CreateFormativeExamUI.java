package ecourse.base.app.UI;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.ExamMagnament.Controllers.CreateFormativeExamController;
import ecourse.base.ExamMagnament.domain.QuestionType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateFormativeExamUI extends AbstractUI {

    Scanner scanner = new Scanner(System.in);
    private final CreateFormativeExamController theController = new CreateFormativeExamController();

    protected boolean doShow() {

        final String examCode = Console.readLine("Enter the Formative Exam code:");
        final String courseCode = Console.readLine("Write the course code of the exam:");

        final String examName = Console.readLine("Please insert the exam name:");
        String examHeaderDescription = "Header Description: ";
        examHeaderDescription += Console.readLine("Please insert the exam header:");
        final String examDate = Console.readLine("Insert the exam date in the following format: dd/mm/yyyy");

        int numberOfSections = Console.readInteger("Please insert the number of sections you want to create:");

        double examCotation = 00.00;

        for(int i = 0; i < numberOfSections; i++) {

            List<QuestionType> perms = Arrays.asList(QuestionType.values());
            QuestionType type = showTypeQuestions(perms);
            String sectionType = "";

            if (type == QuestionType.MULTIPLE_CHOICE) {
                sectionType = "Multiple Choice type question!";
            } else if (type == QuestionType.MATCHING) {
                sectionType = "Matching Answer type question!";
            } else if (type == QuestionType.SHORT_ANSWER) {
                sectionType = "Short Answer type question!";
            } else if (type == QuestionType.TRUE_OR_FALSE) {
                sectionType = "True or False type question!";
            } else if (type == QuestionType.MISSING_WORD) {
                sectionType = "Missing Word Answer type question!";
            } else if (type == QuestionType.NUMERICAL) {
                sectionType = "Numerical Answer type question!";
            }

            String sectionDescription = "Section Description: ";
            sectionDescription += Console.readLine("Please insert the section description:");

            double sectionCotation;

            do {
                System.out.println("Please insert the section cotation:\n");
                sectionCotation = scanner.nextDouble();
                if(examCotation + sectionCotation <= 20){
                    examCotation += sectionCotation;
                    break;
                }
            }while(examCotation <= 20);

            if(examCotation == 20 && i < numberOfSections - 1){
                doShow();
                return false;
            }




            int numberOfQuestionsInSection = Console.readInteger("Please insert the number of questions you want to create in this section:");

            double questionCotation = sectionCotation / numberOfQuestionsInSection;
            theController.createSection(type, numberOfQuestionsInSection, sectionDescription, questionCotation, sectionType);
        }

        try {
            theController.createFormativeExam(examName, examHeaderDescription, examDate, examCode, courseCode);
        } catch (IOException e) {
            System.out.println("Error creating the exam!");
        }


        System.out.println("Foarmative Exam created successfully!");


        return false;
    }

    @Override
    public String headline() {
        return "Create Question";
    }

    private QuestionType showTypeQuestions(final List<QuestionType> perms) {
        int option;
        System.out.println("----------Type Question------------");
        System.out.println("0." + perms.get(0).toString());
        System.out.println("1." + perms.get(1).toString());
        System.out.println("2." + perms.get(2).toString());
        System.out.println("3." + perms.get(3).toString());
        System.out.println("4." + perms.get(4).toString());
        System.out.println("5." + perms.get(5).toString());
        do {
            option = Console.readInteger("Choose the type of the question in section:");
        }while(option<0 || option>5);

        return perms.get(option);
    }
}
