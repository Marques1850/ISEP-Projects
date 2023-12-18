package ecourse.base.app.exams;

import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.util.List;

public class ListCourseExamsService {
    private ExamRepository examRepo;
    private CourseRepository courseRepo;

    public ListCourseExamsService(ExamRepository examRepo, CourseRepository courseRepo) {
        this.examRepo = examRepo;
        this.courseRepo = courseRepo;
    }
    public List<Exam> allCourseExams(String courseID ){
        if ( CourseCode.isValidCode(courseID) && courseRepo.verifyCourse(courseID) ){
            return examRepo.findAllOfCourse(CourseCode.valueOf(courseID));
        } else {
            return null;
        }
    }
}
