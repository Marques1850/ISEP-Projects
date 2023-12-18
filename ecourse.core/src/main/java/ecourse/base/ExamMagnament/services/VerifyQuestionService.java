package ecourse.base.ExamMagnament.services;


import ecourse.base.ExamMagnament.FormativeExamLexer;
import ecourse.base.ExamMagnament.FormativeExamParser;
import ecourse.base.ExamMagnament.domain.QuestionType;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import ecourse.base.ExamMagnament.ExamLexer;
import ecourse.base.ExamMagnament.ExamParser;

import java.io.IOException;




public class VerifyQuestionService {

    public VerifyQuestionService(){
    }

    public void verify (QuestionType type, String ficheiro) throws IOException {
        if(type == QuestionType.MULTIPLE_CHOICE){
            verifyMultipleChoice(ficheiro);
        }
        else if(type == QuestionType.TRUE_OR_FALSE){
            verifyTrueOrFalse(ficheiro);
        }
        else if(type == QuestionType.SHORT_ANSWER){
            verifyShortAnswer(ficheiro);
        }
        else if(type == QuestionType.MISSING_WORD){
            verifyMissingWord(ficheiro);
        }
        else if(type == QuestionType.MATCHING){
            verifyMatching(ficheiro);
        }
        else if(type == QuestionType.NUMERICAL){
            verifyNumerical(ficheiro);
        }

    }

    private void verifyNumerical(String ficheiro) {
        try {
            CharStream fis = CharStreams.fromFileName(ficheiro);
            FormativeExamLexer lexer = new FormativeExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FormativeExamParser parser = new FormativeExamParser(tokens);
            ParseTree tree = parser.numericalAnswer(); // parse
            System.out.println("A questão numérica é válida!");
        } catch (IOException e) {
            System.out.println("A questão numérica não é válida!");
        }
    }

    private void verifyMatching(String ficheiro) {
        try {
            CharStream fis = CharStreams.fromFileName(ficheiro);
            FormativeExamLexer lexer = new FormativeExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FormativeExamParser parser = new FormativeExamParser(tokens);
            ParseTree tree = parser.matchingAnswer(); // parse
            System.out.println("A questão de correspondência é válida!");
        } catch (IOException e) {
            System.out.println("A questão de correspondência não é válida!");
        }
    }

    private void verifyMissingWord(String ficheiro) {
        try {
            CharStream fis = CharStreams.fromFileName(ficheiro);
            FormativeExamLexer lexer = new FormativeExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FormativeExamParser parser = new FormativeExamParser(tokens);
            ParseTree tree = parser.missingWordAnswer(); // parse
            System.out.println("A questão de resposta de palavra em falta é válida!");
        } catch (IOException e) {
            System.out.println("A questão de resposta de palavra em falta não é válida!");
        }
    }

    private void verifyShortAnswer(String ficheiro) {
        try {
            CharStream fis = CharStreams.fromFileName(ficheiro);
            FormativeExamLexer lexer = new FormativeExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FormativeExamParser parser = new FormativeExamParser(tokens);
            ParseTree tree = parser.shortAnswer(); // parse
            System.out.println("A questão de resposta curta é válida!");
        } catch (IOException e) {
            System.out.println("A questão de resposta curta não é válida!");
        }
    }

    private void verifyTrueOrFalse(String ficheiro) {
        try {
            CharStream fis = CharStreams.fromFileName(ficheiro);
            FormativeExamLexer lexer = new FormativeExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FormativeExamParser parser = new FormativeExamParser(tokens);
            ParseTree tree = parser.trueFalse(); // parse
            System.out.println("A questão de verdadeiro ou falso é válida!");
        } catch (IOException e) {
            System.out.println("A questão de verdadeiro ou falso não é válida!");
        }
    }

    private void verifyMultipleChoice(String ficheiro) {
        try {
            CharStream fis = CharStreams.fromFileName(ficheiro);
            FormativeExamLexer lexer = new FormativeExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FormativeExamParser parser = new FormativeExamParser(tokens);
            ParseTree tree = parser.multipleChoice(); // parse
            System.out.println("A questão de escolha múltipla é válida!");
        } catch (IOException e) {
            System.out.println("A questão de escolha múltipla não é válida!");
        }
    }


}
