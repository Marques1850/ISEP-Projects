package ecourse.base.ExamMagnament.Controllers;

import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.ExamReadFile;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.Question;
import ecourse.base.ExamMagnament.domain.QuestionDto;
import ecourse.base.ExamMagnament.domain.QuestionType;
import ecourse.base.ExamMagnament.repositories.QuestionRepository;
import ecourse.base.ExamMagnament.services.VerifyExamService;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import ecourse.base.ExamMagnament.services.VerifyQuestionService;


public class CreateQuestionController {

    private final QuestionRepository questionRepository = PersistenceContext.repositories().questions();

    private final VerifyQuestionService verifyQuestionService = new VerifyQuestionService();



    public void verifyQuestion(QuestionType type, String fileName) throws IOException {
        String path = "C:/Faculdade/ISEP 2º Ano/sem4pi-22-23-41/ecourse.core/src/main/java/ecourse/base/ExamMagnament/";
        String finalPath = path.concat(fileName + ".txt");
        verifyQuestionService.verify(type, finalPath);
    }

    public QuestionDto createQuestion(QuestionType type, String fileName) {
        String path = "C:/Faculdade/ISEP 2º Ano/sem4pi-22-23-41/ecourse.core/src/main/java/ecourse/base/ExamMagnament/";
        String finalPath = path.concat(fileName + ".txt");
        List<String> lines = new ExamReadFile().FileToString(finalPath);
        Question question = new Question(type, lines);
        questionRepository.save(question);
        return QuestionDto.toDto(question);
    }
}
