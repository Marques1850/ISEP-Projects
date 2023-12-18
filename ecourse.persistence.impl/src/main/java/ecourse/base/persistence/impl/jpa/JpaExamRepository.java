package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.ExamCode;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JpaExamRepository
        extends JpaAutoTxRepository<Exam, ExamCode, ExamCode>
        implements ExamRepository {

    public JpaExamRepository(final  String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(), "code");
    }
    public JpaExamRepository(final TransactionalContext autoTx) {
        super(autoTx, "code");
    }


    @Override
    public List<Exam> findAllExams() {
        Iterable<Exam> iterable = findAll();
        List<Exam> exams = new ArrayList<>();
        for (Exam exam : iterable) {
            exams.add(exam);
        }
        return exams;
    }

    @Override
    public List<Exam> findAllOfCourse(CourseCode courseCode) {
        final Map<String, Object> params = new HashMap<>();
        params.put("CourseCode", courseCode);
        return match("courseCode=:CourseCode", params);
    }


    @Override
    public Exam findByCode(ExamCode code) {
        final Map<String, Object> params = new HashMap<>();
        params.put("Code", code);
        return matchOne("code=:Code", params).get();
    }
}


