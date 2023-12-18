package ecourse.base.ExamMagnament.services;



import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import ecourse.base.ExamMagnament.ExamLexer;
import ecourse.base.ExamMagnament.ExamParser;

import java.io.IOException;

public class VerifyExamService {

    public void verify (String ficheiro) throws IOException {
        try {
            CharStream fis = CharStreams.fromFileName(ficheiro);
            ExamLexer lexer = new ExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ExamParser parser = new ExamParser(tokens);
            ParseTree tree = parser.exam(); // parse
            System.out.println("O exame é válido!");
        } catch (IOException e) {
            System.out.println("O exame não é válido!");
        }

    }

}
