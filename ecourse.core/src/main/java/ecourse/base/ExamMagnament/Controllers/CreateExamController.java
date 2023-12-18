package ecourse.base.ExamMagnament.Controllers;

import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.ExamReadFile;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.services.VerifyExamService;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateExamController {

    private final ExamRepository examRepository = PersistenceContext.repositories().exams();

    private final VerifyExamService verifyExamService = new VerifyExamService();

    public void verifyExam(String fileName) throws IOException {
        String path = "C:/Faculdade/ISEP 2º Ano/sem4pi-22-23-41/ecourse.core/src/main/java/ecourse/base/ExamMagnament/";
        String finalPath = path.concat(fileName + ".txt");
        verifyExamService.verify(finalPath);
    }

    public ExamDto createExam(String code, String title, String courseCode, String openDate, String closeDate, String filename) throws ParseException {
        String path = "C:/Faculdade/ISEP 2º Ano/sem4pi-22-23-41/ecourse.core/src/main/java/ecourse/base/ExamMagnament/";
        String finalPath = path.concat(filename + ".txt");
        List<String> lines = new ExamReadFile().FileToString(finalPath);
        java.util.Date opendate=new SimpleDateFormat("dd/MM/yyyy").parse(openDate);
        java.util.Date closedate=new SimpleDateFormat("dd/MM/yyyy").parse(closeDate);
        Exam exam = new Exam(code, title, CourseCode.valueOf(courseCode), opendate, closedate, lines);
        examRepository.save(exam);
        return ExamDto.toDto(exam);
    }
}
