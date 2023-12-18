package ecourse.base.ExamMagnament.services;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import ecourse.base.ExamMagnament.FormativeExamLexer;
import ecourse.base.ExamMagnament.FormativeExamParser;

public class VerifyFormativeExamService {

    public void verify (String ficheiro) throws IOException {
        try {
            CharStream fis = CharStreams.fromFileName(ficheiro);
            FormativeExamLexer lexer = new FormativeExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FormativeExamParser parser = new FormativeExamParser(tokens);
            ParseTree tree = parser.exam(); // parse
            System.out.println("O exame é válido!");
        } catch (IOException e) {
            System.out.println("O exame não é válido!");
        }

    }

    public VerifyFormativeExamService(){
    }

}
