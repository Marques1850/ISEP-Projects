package ecourse.base.ExamMagnament.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import ecourse.base.ExamMagnament.domain.Question;
import ecourse.base.ExamMagnament.domain.QuestionType;

import java.util.List;

public interface QuestionRepository extends DomainRepository<Long, Question> {
    List<Question> findbytype(QuestionType type);
}
