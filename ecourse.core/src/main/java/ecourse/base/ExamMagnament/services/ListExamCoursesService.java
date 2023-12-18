package ecourse.base.ExamMagnament.services;

import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.FormativeExam;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.ExamMagnament.repositories.FormativeExamRepository;
import ecourse.base.usermanagement.domain.Course.Course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListExamCoursesService {

    public List<Exam> getFutureExams(List<Course> courses,ExamRepository repo){
        List<Exam> exams = new ArrayList<>();
        for ( Course course: courses){
            exams.addAll(repo.findAllOfCourse(course.code()));
        }
        exams.removeIf(exam -> exam.Closedate().date.before(new Date()));

        return exams;
    }

    public List<Exam> getTakeableExams(List<Course> courses, ExamRepository repoE) {
        List<Exam> exams = new ArrayList<>();
        for ( Course course: courses){
            exams.addAll(repoE.findAllOfCourse(course.code()));
        }
        exams.removeIf(exam -> exam.Closedate().date.before(new Date()));
        exams.removeIf(exam -> exam.OpenDate().after(new Date()));

        return exams;
    }

    public List<FormativeExam> getTakeableFormativeExams(List<Course> courses, FormativeExamRepository repoE) {
        List<FormativeExam> exams = new ArrayList<>();
        for ( Course course: courses){
            exams.addAll(repoE.findAllOfCourse(course.code()));
        }

        return exams;
    }
}
