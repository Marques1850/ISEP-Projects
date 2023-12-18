package ecourse.base.ExamMagnament.services;

import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.usermanagement.domain.Course.Course;

import java.util.ArrayList;
import java.util.List;

public class ListExamGradesCourseService {
    public List<ExamGrade> findExamGrades(Course course, ExamRepository repoE) {
        List<ExamGrade> examGrades = new ArrayList<>();
        List<Exam> exams = repoE.findAllOfCourse(course.code());
        for (Exam exam : exams) {
            examGrades.addAll(exam.getGrades());
        }
        return examGrades;
    }

}
