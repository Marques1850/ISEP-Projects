package ecourse.base.ExamMagnament.Controllers;

import eapli.framework.general.domain.model.EmailAddress;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.ExamCode;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

public class CreateExamGradeController {

    private final ExamRepository examRepository = PersistenceContext.repositories().exams();
    private final eCourseUserRepository userRepository = PersistenceContext.repositories().eCourseUsers();

    public void createExamGrade(String examCode, String studentEmail, float grade) {
        Exam exam = examRepository.findByCode(ExamCode.valueOf(examCode));
        eCourseSystemUser student = userRepository.searchUser(EmailAddress.valueOf(studentEmail)).get();
        ExamGrade examGrade = new ExamGrade(student, grade);
        exam.addExamGrade(examGrade);
        examRepository.save(exam);

    }
}
