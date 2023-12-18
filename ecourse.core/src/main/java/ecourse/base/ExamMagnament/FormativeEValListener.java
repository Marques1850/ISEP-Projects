package ecourse.base.ExamMagnament;

import eapli.framework.io.util.Console;
import ecourse.base.ExamMagnament.ForListener.*;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FormativeEValListener extends ecourse.base.ExamMagnament.FormativeExamBaseListener {

    Scanner sc = new Scanner(System.in);
    private double examGrade;
    private String examDate;
    private int sectionNumber;
    private int questionNumber;

    private String sectionTypeQuestion;

    private List<String> phraseList;

    private MultipleChoice multipleChoice;
    private MissingAnswer missingAnswer;
    private ShortQuestion shortQuestion;
    private MatchingQuestion matchingQuestion;
    private TrueOrFalse trueOrFalse;
    private NumericalQuestion numericalQuestion;
    private int choiceNumber;

    private double questionCotation;

    private final List<ExamGrade> grades;
    private final eCourseSystemUser user;


    public FormativeEValListener(List<ExamGrade> grades, eCourseSystemUser user) {
        this.sectionNumber = 0;
        this.phraseList = new ArrayList<String>();
        this.examGrade = 0;
        this.grades = grades;
        this.user = user;
    }

    @Override
    public void exitExam(ecourse.base.ExamMagnament.FormativeExamParser.ExamContext ctx) {
        System.out.println("\n");
        System.out.println("Your grade is: " + examGrade);
        //grades.add(new ExamGrade(user, (float) examGrade));
    }

    @Override
    public void exitTitle(ecourse.base.ExamMagnament.FormativeExamParser.TitleContext ctx) {
        String title = phraseList.get(0);
        System.out.println(title);
        phraseList.clear();
    }

    @Override
    public void exitPhrase(ecourse.base.ExamMagnament.FormativeExamParser.PhraseContext ctx) {
        String frase = "";
        for(int i = 0; i< ctx.WORD().size(); i++){
            frase= frase.concat(ctx.WORD().get(i).getText()+" ");
        }
        phraseList.add(frase);
    }

    @Override
    public void exitHeader(ecourse.base.ExamMagnament.FormativeExamParser.HeaderContext ctx) {
        String header = phraseList.get(0);
        System.out.println("Exam Description: " + header);
        phraseList.clear();
    }

    @Override
    public void exitFinalgrade(ecourse.base.ExamMagnament.FormativeExamParser.FinalgradeContext ctx) {
        examGrade = Double.parseDouble(ctx.REAL_NUMBER().getText());
    }

    @Override
    public void exitDate(ecourse.base.ExamMagnament.FormativeExamParser.DateContext ctx) {
        examDate = ctx.DATE().getText();
        System.out.println("Date: " + examDate);
    }

    @Override
    public void enterSection(ecourse.base.ExamMagnament.FormativeExamParser.SectionContext ctx) {
        sectionNumber++;
        questionNumber = 0;
        if(sectionNumber == Integer.parseInt(ctx.NUMBER().getText())) {
            System.out.println("\n");
            System.out.println("Section " + sectionNumber + ":");
        }
        sectionTypeQuestion = "";
        String sectionDescription = ctx.sectionDescription().phrase().getText();
        System.out.println("Section Description: " + sectionDescription);
        if(ctx.QUESTIONTYPETRUEORFALSE() != null){
            System.out.println("Section Type: " + ctx.QUESTIONTYPETRUEORFALSE().getText());
        } else if(ctx.QUESTIONTYPEMULTIPLECHOICE() != null){
            System.out.println("Section Type: " + ctx.QUESTIONTYPEMULTIPLECHOICE().getText());
        } else if(ctx.QUESTIONTYPESHORTANSWER() != null){
            System.out.println("Section Type: " + ctx.QUESTIONTYPESHORTANSWER().getText());
        } else if(ctx.QUESTIONTYPENUMERICALANSWER() != null){
            System.out.println("Section Type: " + ctx.QUESTIONTYPENUMERICALANSWER().getText());
        } else if(ctx.QUESTIONTYPEMATCHINGANSWER() != null){
            System.out.println("Section Type: " + ctx.QUESTIONTYPEMATCHINGANSWER().getText());
        } else if(ctx.QUESTIONTYPEMISSINGWORDANSWER() != null){
            System.out.println("Section Type: " + ctx.QUESTIONTYPEMISSINGWORDANSWER().getText());
        }
        phraseList.clear();
    }

    @Override
    public void exitCotation(ecourse.base.ExamMagnament.FormativeExamParser.CotationContext ctx) {
        questionCotation = Double.parseDouble(ctx.REAL_NUMBER().getText());
    }

    @Override
    public void enterQtmultiple(ecourse.base.ExamMagnament.FormativeExamParser.QtmultipleContext ctx) {
        choiceNumber = 0;
        questionNumber++;
        multipleChoice = new MultipleChoice();
        phraseList.clear();
    }

    @Override
    public void exitQtmultiple(ecourse.base.ExamMagnament.FormativeExamParser.QtmultipleContext ctx) {
        if(questionNumber == Integer.parseInt(ctx.NUMBER().getText())) {
            System.out.println("\n" + "Question " + questionNumber + ":");
        }
        multipleChoice.setCotation(questionCotation);
        choiceNumber = 0;
        System.out.println("Question Cotation: " + "( " + questionCotation + " )");

        String questionDescription = phraseList.get(0);
        multipleChoice.addQuestion(questionDescription);
        System.out.println("Question Description: " + questionDescription);
        List<String> options = multipleChoice.getOptions();
        for(int i = 0; i< options.size(); i++){
            System.out.println("Option " + (i+1) + ": " + options.get(i));
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        String answer = Console.readLine("Chose the option: ");
        if(answer.equals(multipleChoice.getCorrectOption())) {
            examGrade += multipleChoice.getCotation();
            System.out.println("Correct answer!");
        }
        phraseList.clear();
    }

    @Override
    public void enterQttrueFalse(ecourse.base.ExamMagnament.FormativeExamParser.QttrueFalseContext ctx) {
        choiceNumber = 0;
        questionNumber++;
        trueOrFalse = new TrueOrFalse();
        phraseList.clear();
    }

    @Override
    public void exitQttrueFalse(ecourse.base.ExamMagnament.FormativeExamParser.QttrueFalseContext ctx) {
        if(questionNumber == Integer.parseInt(ctx.NUMBER().getText())) {
            System.out.println("\n" + "Question " + questionNumber + ":");
        }
        trueOrFalse.setCotation(questionCotation);
        choiceNumber = 0;

        String questionDescription = phraseList.get(0);
        trueOrFalse.addQuestion(questionDescription);
        boolean correct = false;
        if(ctx.trueFalse().AST() != null){
            correct = true;
        }
        System.out.println("True or False questions: " + questionDescription);
        System.out.println("-----------------------------------------------------------------------------------------");
        String answer = Console.readLine("Wrtie if is true or false: ");
        if((answer.equals("true") || answer.equals("TRUE") || answer.equals("True") || answer.equals("T") || answer.equals("t"))
                && correct) {
            examGrade += trueOrFalse.getCotation();
            System.out.println("Correct answer!");
        } else if((answer.equals("false") || answer.equals("FALSE") || answer.equals("False") || answer.equals("F") || answer.equals("f"))
                && !correct) {
            examGrade += trueOrFalse.getCotation();
            System.out.println("Correct answer!");
        }
        phraseList.clear();
    }

    @Override
    public void enterQtshortAnswer(ecourse.base.ExamMagnament.FormativeExamParser.QtshortAnswerContext ctx) {
        choiceNumber = 0;
        questionNumber++;
        shortQuestion = new ShortQuestion();
        phraseList.clear();
    }

    @Override
    public void exitQtshortAnswer(ecourse.base.ExamMagnament.FormativeExamParser.QtshortAnswerContext ctx) {
        if(questionNumber == Integer.parseInt(ctx.NUMBER().getText())) {
            System.out.println("\n" + "Question " + questionNumber + ":");
        }
        shortQuestion.setCotation(questionCotation);
        choiceNumber = 0;

        String questionDescription = phraseList.get(0);
        shortQuestion.addQuestion(questionDescription);
        System.out.println("Question Description: " + questionDescription);
        System.out.println("-----------------------------------------------------------------------------------------");
        String answer = Console.readLine("Write your answer: ");
        if(answer.contains(shortQuestion.getWordNeeded().substring(0, shortQuestion.getWordNeeded().length()-1))) {
            examGrade += shortQuestion.getCotation();
            System.out.println("Correct answer!");
        }
        phraseList.clear();
    }

    @Override
    public void enterQtnumericalAnswer(ecourse.base.ExamMagnament.FormativeExamParser.QtnumericalAnswerContext ctx) {
        choiceNumber = 0;
        questionNumber++;
        numericalQuestion = new NumericalQuestion();
        phraseList.clear();
    }

    @Override
    public void exitQtnumericalAnswer(ecourse.base.ExamMagnament.FormativeExamParser.QtnumericalAnswerContext ctx) {
        if(questionNumber == Integer.parseInt(ctx.NUMBER().getText())) {
            System.out.println("\n" + "Question " + questionNumber + ":");
        }
        numericalQuestion.setCotation(questionCotation);
        choiceNumber = 0;

        System.out.println("Question Description: " + numericalQuestion.getQuestion());
        System.out.println("-----------------------------------------------------------------------------------------");
        String answer = Console.readLine("Write your answer: ");
        double answerDouble = Double.parseDouble(answer);
        if( (answerDouble == numericalQuestion.getAnswer()) ||
                (answerDouble >= (numericalQuestion.getAnswer() - numericalQuestion.getTolerance()) && (answerDouble <= numericalQuestion.getAnswer() + numericalQuestion.getTolerance()))) {
            examGrade += numericalQuestion.getCotation();
            System.out.println("Correct answer!");
        }
    }

    @Override
    public void enterQtmatchingAnswer(ecourse.base.ExamMagnament.FormativeExamParser.QtmatchingAnswerContext ctx) {
        choiceNumber = 0;
        questionNumber++;
        matchingQuestion = new MatchingQuestion();
        phraseList.clear();
    }

    @Override
    public void exitQtmatchingAnswer(ecourse.base.ExamMagnament.FormativeExamParser.QtmatchingAnswerContext ctx) {
        if(questionNumber == Integer.parseInt(ctx.NUMBER().getText())) {
            System.out.println("\n" + "Question " + questionNumber + ":");
        }
        matchingQuestion.setCotation(questionCotation);
        choiceNumber = 0;

        String questionDescription = phraseList.get(0);
        matchingQuestion.addQuestion(questionDescription);
        System.out.println("Question Description: ");
        int i = 0;
        int j = 1;
        int k = 0;
        String op = "A";
        int espaco = 35;
        while(k < phraseList.size()){
            matchingQuestion.addWordToMatch(phraseList.get(i));
            matchingQuestion.addWordToMatchWith(phraseList.get(j));
            matchingQuestion.addToMap(phraseList.get(i), phraseList.get(j));
            i+=2;
            j+=2;
            k+=2;
        }

        i = 0;
        j = 0;
        k = 1;
        List<String> list = new ArrayList<>();
        for (int l = 0; l < matchingQuestion.getListWordToMatchWith().size(); l++) {
            list.add(matchingQuestion.getWordToMatchWith(l));
        }
        List<String> match = new ArrayList<>();
        List<String> matchWith = new ArrayList<>();
        while(j < matchingQuestion.getListWordToMatchWith().size()){
            int size = matchingQuestion.getWordToMatch(i).length();
            String space = "";
            for(int l = 0; l < espaco - size; l++){
                space = space.concat(" ");
            }

            Random rand = new Random();
            int s = rand.nextInt(list.size());

            System.out.println(op + "." + matchingQuestion.getWordToMatch(i) + space + k + "." + list.get(s));
            match.add(op + "." + matchingQuestion.getWordToMatch(i));
            matchWith.add(k + "." + list.get(s));
            list.remove(s);

            i++;
            j++;
            op = String.valueOf((char)(op.charAt(0) + 1));
            k++;
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        String opAns = "A";
        System.out.println("Write the correct answer: like A-1");
        List<String> opsAlreadyUsed = new ArrayList<>();
        while(!op.equals("A")){
            String answer = sc.nextLine();
            char op1 = answer.charAt(0);
            char op2 = answer.charAt(2);
            String leftWord = "";
            if(!opsAlreadyUsed.contains(String.valueOf(op1))) {

                for (String s : match) {
                    if (s.charAt(0) == op1) {
                        leftWord = s.substring(2);
                    }
                }
                String rightWord = "";
                for (String s : matchWith) {
                    if (s.charAt(0) == op2) {
                        rightWord = s.substring(2);
                    }
                }
                if (matchingQuestion.getMap().containsKey(leftWord) && matchingQuestion.getMap().get(leftWord).equals(rightWord)) {
                    examGrade += matchingQuestion.getCotation() / matchingQuestion.getMap().size();
                    System.out.println("Correct answer!");
                }

                opsAlreadyUsed.add(String.valueOf(op1));


                opAns = String.valueOf((char) (opAns.charAt(0) + 1));
                op = String.valueOf((char) (op.charAt(0) - 1));


            } else {
                System.out.println("You already used this option!");
            }

        }
        phraseList.clear();
    }

    @Override
    public void enterQtmissingWordAnswer(ecourse.base.ExamMagnament.FormativeExamParser.QtmissingWordAnswerContext ctx) {
        choiceNumber = 0;
        questionNumber++;
        missingAnswer = new MissingAnswer();
        phraseList.clear();
    }

    @Override
    public void exitQtmissingWordAnswer(ecourse.base.ExamMagnament.FormativeExamParser.QtmissingWordAnswerContext ctx) {
        if(questionNumber == Integer.parseInt(ctx.NUMBER().getText())) {
            System.out.println("\n" + "Question " + questionNumber + ":");
        }
        missingAnswer.setCotation(questionCotation);
        choiceNumber = 0;

        String questionDescription = phraseList.get(0);
        missingAnswer.addQuestion(questionDescription);
        System.out.println("Question Description: " + questionDescription + "______ " + phraseList.get(2));
        System.out.println("-----------------------------------------------------------------------------------------");
        String answer = Console.readLine("Write the missing word: ");
        if(answer.equals(missingAnswer.getMissingWord().substring(0, missingAnswer.getMissingWord().length()-1))) {
            examGrade += missingAnswer.getCotation();
            System.out.println("Correct answer!");
        }
        phraseList.clear();
    }

    @Override
    public void enterMultipleChoice(ecourse.base.ExamMagnament.FormativeExamParser.MultipleChoiceContext ctx) {
        super.enterMultipleChoice(ctx);
    }

    @Override
    public void exitMultipleChoice(ecourse.base.ExamMagnament.FormativeExamParser.MultipleChoiceContext ctx) {
        super.exitMultipleChoice(ctx);
    }

    @Override
    public void exitOption(ecourse.base.ExamMagnament.FormativeExamParser.OptionContext ctx) {
        choiceNumber ++;
        if(ctx.getText().equals("*")){
            multipleChoice.setCorrectOption(choiceNumber);
        }
        String option = phraseList.get(choiceNumber);
        multipleChoice.addOption(option);
    }

    @Override
    public void enterTrueFalse(ecourse.base.ExamMagnament.FormativeExamParser.TrueFalseContext ctx) {
        super.enterTrueFalse(ctx);
    }

    @Override
    public void exitTrueFalse(ecourse.base.ExamMagnament.FormativeExamParser.TrueFalseContext ctx) {
        super.exitTrueFalse(ctx);
    }

    @Override
    public void enterShortAnswer(ecourse.base.ExamMagnament.FormativeExamParser.ShortAnswerContext ctx) {
        super.enterShortAnswer(ctx);
    }

    @Override
    public void exitShortAnswer(ecourse.base.ExamMagnament.FormativeExamParser.ShortAnswerContext ctx) {
        super.exitShortAnswer(ctx);
    }

    @Override
    public void enterNumericalAnswer(ecourse.base.ExamMagnament.FormativeExamParser.NumericalAnswerContext ctx) {
        super.enterNumericalAnswer(ctx);
    }

    @Override
    public void exitNumericalAnswer(ecourse.base.ExamMagnament.FormativeExamParser.NumericalAnswerContext ctx) {
        super.exitNumericalAnswer(ctx);
    }

    @Override
    public void enterMatchingAnswer(ecourse.base.ExamMagnament.FormativeExamParser.MatchingAnswerContext ctx) {
        super.enterMatchingAnswer(ctx);
    }

    @Override
    public void exitMatchingAnswer(ecourse.base.ExamMagnament.FormativeExamParser.MatchingAnswerContext ctx) {
        super.exitMatchingAnswer(ctx);
    }

    @Override
    public void enterAnswer(ecourse.base.ExamMagnament.FormativeExamParser.AnswerContext ctx) {
        super.enterAnswer(ctx);
    }

    @Override
    public void exitAnswer(ecourse.base.ExamMagnament.FormativeExamParser.AnswerContext ctx) {
        super.exitAnswer(ctx);
    }

    @Override
    public void enterMissingWordAnswer(ecourse.base.ExamMagnament.FormativeExamParser.MissingWordAnswerContext ctx) {
        super.enterMissingWordAnswer(ctx);
    }

    @Override
    public void exitMissingWordAnswer(ecourse.base.ExamMagnament.FormativeExamParser.MissingWordAnswerContext ctx) {
        super.exitMissingWordAnswer(ctx);
    }

    @Override
    public void exitEquation(ecourse.base.ExamMagnament.FormativeExamParser.EquationContext ctx) {
        String question = numericalQuestion.getQuestion();
        String questionDescription = ctx.getText();
        String finalQuestion = question + questionDescription + " ";
        numericalQuestion.addQuestion(finalQuestion);
    }

    @Override
    public void exitCorrectAnswer(ecourse.base.ExamMagnament.FormativeExamParser.CorrectAnswerContext ctx) {
        String answer = phraseList.get(1);
        if(missingAnswer != null){
            missingAnswer.addMissingWord(answer);
        }
        if(shortQuestion != null){
            shortQuestion.addWordNeeded(answer);
        }
    }

    @Override
    public void exitCorrecNumericaltAnswer(ecourse.base.ExamMagnament.FormativeExamParser.CorrecNumericaltAnswerContext ctx) {
        String answer = ctx.NUMBER().get(0).getText();
        numericalQuestion.setAnswer(Double.parseDouble(answer));
        String tolerance = ctx.NUMBER().get(1).getText();
        numericalQuestion.setTolerance(Double.parseDouble(tolerance));
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }
}
