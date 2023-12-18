package ecourse.base.ExamMagnament.domain;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "QUESTION")
public class Question implements AggregateRoot<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Long id;

    @ElementCollection
    @Column(name = "QUESTION_CONTENT")
    private List<String> content;

    @Column(name = "QUESTION_TYPE")
    private QuestionType type;


    public Question(QuestionType type, List<String> content) {
        this.type = type;
        this.content = content;
    }

    public Question() {
        this.content =  new ArrayList<>();
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public Long identity() {
        return id;
    }

    public List<String> content() {
        return content;
    }

    public QuestionType type() {
        return type;
    }
}
