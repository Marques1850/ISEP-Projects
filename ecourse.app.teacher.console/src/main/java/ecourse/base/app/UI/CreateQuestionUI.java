package ecourse.base.app.UI;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.ExamMagnament.Controllers.CreateQuestionController;
import ecourse.base.ExamMagnament.domain.QuestionDto;
import ecourse.base.ExamMagnament.domain.QuestionType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CreateQuestionUI extends AbstractUI {


    private final CreateQuestionController theController = new CreateQuestionController();
    @Override
    protected boolean doShow() {

        List<QuestionType> perms = Arrays.asList(QuestionType.values());
        QuestionType type = showTypeQuestions(perms);
        final String fileName = Console.readLine("Please insert the question file name:");

        QuestionDto questionDto;
        try {
            theController.verifyQuestion(type, fileName);
            questionDto = theController.createQuestion(type, fileName);
            System.out.println("This is the question you created: " + questionDto.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Question created successfully!");


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
            option = Console.readInteger("Choose the type of the question:");
        }while(option<0 || option>5);

        return perms.get(option);
    }
}
