package ecourse.base.ExamMagnament.services;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourse.base.ExamMagnament.ExamEvalListner;
import ecourse.base.ExamMagnament.FormativeEValListener;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.ExamMagnament.domain.FormativeExam;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.ExamMagnament.repositories.FormativeExamRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.Course;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TakeExamService {

    private final ListExamCoursesService listExamCoursesService = new ListExamCoursesService();

    private eCourseSystemUser fuser;

    public List<Exam> getExams(SystemUser user, ExamRepository repoE, eCourseUserRepository repo) {
        eCourseSystemUser eUser= findUser(user,repo);
        fuser = eUser;
        List<Course> courses= getStudentCourses(eUser);
        return listExamCoursesService.getTakeableExams(courses,repoE);
    }

    public List<FormativeExam> getFormativeExams(SystemUser user, FormativeExamRepository repoE, eCourseUserRepository repo) {
        eCourseSystemUser eUser= findUser(user,repo);
        fuser = eUser;
        List<Course> courses= getStudentCourses(eUser);
        return listExamCoursesService.getTakeableFormativeExams(courses,repoE);
    }

    public eCourseSystemUser findUser(SystemUser user, eCourseUserRepository repo ){
        if(repo.searchUser(user.email()).isPresent())
            return repo.searchUser(user.email()).get();
        else
            return null;

    }

    public List<Course> getStudentCourses(eCourseSystemUser user){
        return user.coursesLearning();
    }

    public void takeExam(Exam exam) {
        List<String> content = exam.getContent();
        try {
            Files.write(Paths.get("exam.txt"), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ExamGrade> grades = exam.getGrades();
        try {
            CharStream fis = CharStreams.fromFileName("C:/Faculdade/ISEP 2º Ano/sem4pi-22-23-41/ecourse.core/src/main/java/ecourse/base/ExamMagnament/ExamTest.txt");
            ecourse.base.ExamMagnament.ExamLexer lexer = new ecourse.base.ExamMagnament.ExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ecourse.base.ExamMagnament.ExamParser parser = new ecourse.base.ExamMagnament.ExamParser(tokens);
            ParseTree tree = parser.exam(); // parse
            ExamEvalListner eval = new ExamEvalListner(grades, fuser);
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(eval, tree);
            System.out.println("Exam finished!");
        } catch (IOException e) {
            System.out.println("Exam not finished!");
        }

    }

    public void takeFormativeExam(FormativeExam exam) {
        List<String> content = exam.getContent();
        try {
            Files.write(Paths.get("exam.txt"), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ExamGrade> grades = exam.getGrades();
        try {
            CharStream fis = CharStreams.fromFileName("exam.txt");
            ecourse.base.ExamMagnament.FormativeExamLexer lexer = new ecourse.base.ExamMagnament.FormativeExamLexer(fis);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ecourse.base.ExamMagnament.FormativeExamParser parser = new ecourse.base.ExamMagnament.FormativeExamParser(tokens);
            ParseTree tree = parser.exam(); // parse
            FormativeEValListener eval = new FormativeEValListener(grades, fuser);
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(eval, tree);
            System.out.println("Exam finished!");
        } catch (IOException e) {
            System.out.println("Exam not finished!");
        }

    }
}
