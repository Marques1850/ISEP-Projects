package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.ExamMagnament.domain.ExamCode;
import ecourse.base.ExamMagnament.domain.FormativeExam;
import ecourse.base.ExamMagnament.repositories.FormativeExamRepository;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.util.*;


public class JpaFormativeExamRepository
        extends JpaAutoTxRepository<FormativeExam, ExamCode, ExamCode>
        implements FormativeExamRepository {

    public JpaFormativeExamRepository(final  String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(), "code");
    }
    public JpaFormativeExamRepository(final TransactionalContext autoTx) {
        super(autoTx, "code");
    }

    @Override
    public Collection<? extends FormativeExam> findAllOfCourse(CourseCode courseCode) {
        final Map<String, Object> params = new HashMap<>();
        params.put("CourseCode", courseCode);
        return match("courseCode=:CourseCode", params);
    }


    @Override
    public FormativeExam findByCode(ExamCode code) {
        final Map<String, Object> params = new HashMap<>();
        params.put("Code", code);
        return matchOne("code=:Code", params).get();
    }
}


