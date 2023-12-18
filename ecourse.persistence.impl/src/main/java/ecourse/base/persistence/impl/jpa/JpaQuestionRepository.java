package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.ExamMagnament.domain.Question;
import ecourse.base.ExamMagnament.domain.QuestionType;
import ecourse.base.ExamMagnament.repositories.QuestionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaQuestionRepository extends JpaAutoTxRepository<Question, Long, Long> implements QuestionRepository {

    public JpaQuestionRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }
    public JpaQuestionRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "id");
    }

    @Override
    public List<Question> findbytype(QuestionType type) {
        final Map<String, Object> params = new HashMap<>();
        params.put("Type", type);
        return match("type=:Type", params);

    }
}
