package ecourse.base.ExamMagnament.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import ecourse.base.ExamMagnament.domain.ExamCode;
import ecourse.base.ExamMagnament.domain.FormativeExam;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.util.Collection;

public interface FormativeExamRepository extends DomainRepository<ExamCode, FormativeExam> {

    Collection<? extends FormativeExam> findAllOfCourse(CourseCode courseCode);
    FormativeExam findByCode(ExamCode code);
}
