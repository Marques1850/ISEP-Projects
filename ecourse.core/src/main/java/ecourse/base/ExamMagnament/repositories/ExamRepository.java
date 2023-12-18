package ecourse.base.ExamMagnament.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.ExamCode;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.util.List;

public interface ExamRepository extends DomainRepository<ExamCode, Exam> {
    List<Exam> findAllExams();
    List<Exam> findAllOfCourse(CourseCode courseCode);
    Exam findByCode(ExamCode code);
}