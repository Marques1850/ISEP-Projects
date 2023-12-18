package ecourse.base.ExamMagnament.Controllers;

import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.ExamMagnament.domain.FormativeExam;
import ecourse.base.ExamMagnament.domain.Question;
import ecourse.base.ExamMagnament.domain.QuestionType;
import ecourse.base.ExamMagnament.repositories.FormativeExamRepository;
import ecourse.base.ExamMagnament.repositories.QuestionRepository;
import ecourse.base.ExamMagnament.services.ServiceGetQuestions;
import ecourse.base.ExamMagnament.services.VerifyFormativeExamService;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CreateFormativeExamController {

    private final QuestionRepository questionRepository = PersistenceContext.repositories().questions();
    private final FormativeExamRepository formativeExamRepository = PersistenceContext.repositories().formativeexams();
    private static ServiceGetQuestions serviceGetQuestions = new ServiceGetQuestions();
    private final VerifyFormativeExamService verifyFormativeExamService = new VerifyFormativeExamService();

    private static List<List<String>> sections = new ArrayList<>();

    private static int sectionNumber = 0;
    public void createSection(QuestionType type, int numberOfQuestionsInSection, String sectionDescription, double questionCotation, String sectionType) {
        sectionNumber++;
        String section = "Section: ";
        section += sectionNumber;

        List<Question> sectionQuestion = serviceGetQuestions.getQuestions(type, numberOfQuestionsInSection, questionRepository);

        List<String> sectionL = new ArrayList<>();

        sectionL.add(section);
        sectionL.add(sectionDescription);
        sectionL.add(sectionType);
        for(int i = 1; i <= numberOfQuestionsInSection; i++){
            sectionL.add("Question: " + i);
            sectionL.add("Question Cotation: ("+questionCotation+")");
            sectionL.addAll(sectionQuestion.get(i - 1).content());
        }

        sections.add(sectionL);
    }

    public void createFormativeExam(String examName, String examHeaderDescription, String examDate, String examCode, String courseCode) throws IOException {
        List<String> exam = new ArrayList<>();

        exam.add(examName);
        exam.add(examHeaderDescription);
        exam.add("Final Grade: 00.00");
        exam.add(examDate);
        for (List<String> section : sections) {
            exam.addAll(section);
        }
        verifyFormativeExam(exam);

        FormativeExam formativeExam = new FormativeExam(examCode, exam, CourseCode.valueOf(courseCode));

        formativeExamRepository.save(formativeExam);
    }


    public void verifyFormativeExam(List<String> exam) throws IOException {
        try {
            Files.write(Paths.get("formativeexam.txt"), exam);
        } catch (IOException e) {
            e.printStackTrace();
        }
        verifyFormativeExamService.verify("formativeexam.txt");
    }
}
