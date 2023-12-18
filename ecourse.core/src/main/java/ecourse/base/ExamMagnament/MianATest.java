package ecourse.base.ExamMagnament;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class MianATest {


    public static void main(String[] args) {
        /*
        try {
            CharStream fis = CharStreams.fromFileName("C:/Faculdade/ISEP 2º Ano/sem4pi-22-23-41/ecourse.core/src/main/java/ecourse/base/ExamMagnament/FormativeExamTest.txt");
            ecourse.base.ExamMagnament.FormativeExamLexer lexer = new ecourse.base.ExamMagnament.FormativeExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ecourse.base.ExamMagnament.FormativeExamParser parser = new ecourse.base.ExamMagnament.FormativeExamParser(tokens);
            ParseTree tree = parser.exam(); // parse
            FormativeEValListener eval = new FormativeEValListener();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(eval, tree);
            System.out.println("O exame é válido!");
        } catch (IOException e) {
            System.out.println("O exame não é válido!");
        }

         */

        /*
        try {
            CharStream fis = CharStreams.fromFileName("C:/Faculdade/ISEP 2º Ano/sem4pi-22-23-41/ecourse.core/src/main/java/ecourse/base/ExamMagnament/ExamTest.txt");
            ecourse.base.ExamMagnament.ExamLexer lexer = new ecourse.base.ExamMagnament.ExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ecourse.base.ExamMagnament.ExamParser parser = new ecourse.base.ExamMagnament.ExamParser(tokens);
            ParseTree tree = parser.exam(); // parse
            ExamEvalListner eval = new ExamEvalListner();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(eval, tree);
            System.out.println("O exame é válido!");
        } catch (IOException e) {
            System.out.println("O exame não é válido!");
        }

         */


    }
}
