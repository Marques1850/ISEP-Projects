package ecourse.base.ExamMagnament;
import eapli.framework.io.util.Console;
import ecourse.base.ExamMagnament.ExamBaseListener;
import ecourse.base.ExamMagnament.ForListener.*;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class ExamEvalListner extends ExamBaseListener {

    private final Stack<Integer> stack = new Stack<>();

    Scanner sc = new Scanner(System.in);
    private double examGrade;
    private String examDate;
    private int sectionNumber;
    private int questionNumber;

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


    public ExamEvalListner(List<ExamGrade> grades, eCourseSystemUser user) {
        this.sectionNumber = 0;
        this.phraseList = new ArrayList<String>();
        this.examGrade = 0;
        this.grades = grades;
        this.user = user;
    }


    @Override
    public void exitExam(ecourse.base.ExamMagnament.ExamParser.ExamContext ctx) {
        System.out.println("\n");
        System.out.println("Your grade is: " + examGrade);
        //grades.add(new ExamGrade(user, (float) examGrade));
    }

    @Override
    public void exitTitle(ecourse.base.ExamMagnament.ExamParser.TitleContext ctx) {
        String title = phraseList.get(0);
        System.out.println(title);
        phraseList.clear();
    }

    @Override
    public void exitPhrase(ecourse.base.ExamMagnament.ExamParser.PhraseContext ctx) {
        String frase = "";
        for(int i = 0; i< ctx.WORD().size(); i++){
           frase= frase.concat(ctx.WORD().get(i).getText()+" ");
        }
        phraseList.add(frase);
    }

    @Override
    public void exitHeader(ecourse.base.ExamMagnament.ExamParser.HeaderContext ctx) {
        String header = phraseList.get(0);
        System.out.println("Exam Description: " + header);
        phraseList.clear();
    }

    @Override
    public void exitFinalgrade(ecourse.base.ExamMagnament.ExamParser.FinalgradeContext ctx) {
        examGrade = Double.parseDouble(ctx.REAL_NUMBER().getText());
    }

    @Override
    public void exitDate(ecourse.base.ExamMagnament.ExamParser.DateContext ctx) {
        examDate = ctx.DATE().getText();
        System.out.println("Date: " + examDate);
    }

    @Override
    public void enterSection(ecourse.base.ExamMagnament.ExamParser.SectionContext ctx) {
        sectionNumber++;
        questionNumber = 0;
        if(sectionNumber == Integer.parseInt(ctx.NUMBER().getText())) {
            System.out.println("\n");
            System.out.println("Section " + sectionNumber + ":");
        }
    }


    @Override
    public void exitSectionDescription(ecourse.base.ExamMagnament.ExamParser.SectionDescriptionContext ctx) {
        String sectionDescription = phraseList.get(0);
        System.out.println("Section Description: " + sectionDescription);
        phraseList.clear();
    }


    @Override
    public void enterQt(ecourse.base.ExamMagnament.ExamParser.QtContext ctx) {
        choiceNumber = 0;
        questionNumber++;
        if(questionNumber == Integer.parseInt(ctx.NUMBER().getText())) {
            System.out.println("\n" + "Question " + questionNumber + ":");
        }
    }

    @Override
    public void exitQt(ecourse.base.ExamMagnament.ExamParser.QtContext ctx) {
        super.enterQt(ctx);
    }

    @Override
    public void exitCotation(ecourse.base.ExamMagnament.ExamParser.CotationContext ctx) {
        questionCotation = Double.parseDouble(ctx.REAL_NUMBER().getText());
    }

    @Override
    public void exitQuestion(ecourse.base.ExamMagnament.ExamParser.QuestionContext ctx) {
        choiceNumber = 0;
    }

    @Override
    public void enterMultipleChoice(ecourse.base.ExamMagnament.ExamParser.MultipleChoiceContext ctx) {
        multipleChoice = new MultipleChoice();
        multipleChoice.setCotation(questionCotation);
        choiceNumber = 0;
    }

    @Override
    public void exitMultipleChoice(ecourse.base.ExamMagnament.ExamParser.MultipleChoiceContext ctx) {
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
    public void exitOption(ecourse.base.ExamMagnament.ExamParser.OptionContext ctx) {
        choiceNumber ++;
        if(ctx.getText().equals("*")){
            multipleChoice.setCorrectOption(choiceNumber);
        }
        String option = phraseList.get(choiceNumber);
        multipleChoice.addOption(option);

    }

    @Override
    public void enterTrueFalse(ecourse.base.ExamMagnament.ExamParser.TrueFalseContext ctx) {
        trueOrFalse = new TrueOrFalse();
        trueOrFalse.setCotation(questionCotation);
        choiceNumber = 0;
    }

    @Override
    public void exitTrueFalse(ecourse.base.ExamMagnament.ExamParser.TrueFalseContext ctx) {
        System.out.println("True or False questions: ");
        int size = phraseList.size();
        String leters = "A";
        for(int i = 0; i< size; i++){
            if(ctx.tf(i).AST() != null){
                trueOrFalse.setCorrectOption(leters);
            }
            trueOrFalse.addQuestions(phraseList.get(i));
            System.out.println(leters + ") " + phraseList.get(i));
            leters = String.valueOf((char)(leters.charAt(0) + 1));
        }
        leters = "A";
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Write if is true or false: ");
        for(int j = 0; j < trueOrFalse.getQuestions().size(); j++){
            String answer = Console.readLine(leters + ")");
            if((answer.equals("true") || answer.equals("TRUE") || answer.equals("True") || answer.equals("T") || answer.equals("t"))
                    && trueOrFalse.getTrueAnswers().contains(leters)) {
                examGrade += trueOrFalse.getCotation() / trueOrFalse.getQuestions().size();
                System.out.println("Correct answer!");
            } else if((answer.equals("false") || answer.equals("FALSE") || answer.equals("False") || answer.equals("F") || answer.equals("f"))
                    && !trueOrFalse.getTrueAnswers().contains(leters)) {
                examGrade += trueOrFalse.getCotation() / trueOrFalse.getQuestions().size();
                System.out.println("Correct answer!");
            }
            leters = String.valueOf((char)(leters.charAt(0) + 1));
        }
        phraseList.clear();
    }

    @Override
    public void enterShortAnswer(ecourse.base.ExamMagnament.ExamParser.ShortAnswerContext ctx) {
        shortQuestion = new ShortQuestion();
        shortQuestion.setCotation(questionCotation);
    }

    @Override
    public void exitShortAnswer(ecourse.base.ExamMagnament.ExamParser.ShortAnswerContext ctx) {
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
    public void enterNumericalAnswer(ecourse.base.ExamMagnament.ExamParser.NumericalAnswerContext ctx) {
        numericalQuestion = new NumericalQuestion();
        numericalQuestion.setCotation(questionCotation);
    }

    @Override
    public void exitNumericalAnswer(ecourse.base.ExamMagnament.ExamParser.NumericalAnswerContext ctx) {
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
    public void exitEquation(ecourse.base.ExamMagnament.ExamParser.EquationContext ctx) {
        String question = numericalQuestion.getQuestion();
        String questionDescription = ctx.getText();
        String finalQuestion = question + questionDescription + " ";
        numericalQuestion.addQuestion(finalQuestion);
    }

    @Override
    public void enterMatchingAnswer(ecourse.base.ExamMagnament.ExamParser.MatchingAnswerContext ctx) {
        matchingQuestion = new MatchingQuestion();
        matchingQuestion.setCotation(questionCotation);
    }

    @Override
    public void exitMatchingAnswer(ecourse.base.ExamMagnament.ExamParser.MatchingAnswerContext ctx) {
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
    public void enterAnswer(ecourse.base.ExamMagnament.ExamParser.AnswerContext ctx) {
        super.enterAnswer(ctx);
    }

    @Override
    public void exitAnswer(ecourse.base.ExamMagnament.ExamParser.AnswerContext ctx) {
        super.exitAnswer(ctx);
    }

    @Override
    public void enterMissingWordAnswer(ecourse.base.ExamMagnament.ExamParser.MissingWordAnswerContext ctx) {
        missingAnswer = new MissingAnswer();
        missingAnswer.setCotation(questionCotation);
    }

    @Override
    public void exitMissingWordAnswer(ecourse.base.ExamMagnament.ExamParser.MissingWordAnswerContext ctx) {
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
    public void exitCorrectAnswer(ecourse.base.ExamMagnament.ExamParser.CorrectAnswerContext ctx) {
        String answer = phraseList.get(1);
        if(missingAnswer != null){
            missingAnswer.addMissingWord(answer);
        }
        if(shortQuestion != null){
            shortQuestion.addWordNeeded(answer);
        }
    }

    @Override
    public void exitCorrecNumericaltAnswer(ecourse.base.ExamMagnament.ExamParser.CorrecNumericaltAnswerContext ctx) {
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
