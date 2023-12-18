package ecourse.base.PostItMagnament.domain;

import eapli.framework.domain.model.AggregateRoot;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import javax.persistence.*;

@Entity
@Table(name = "POST_IT")
public class PostIt implements AggregateRoot<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_IT_ID")
    private Long id;
    @Column(name = "POST_IT_CONTENT")
    private Content content;
    @OneToOne
    private eCourseSystemUser ownUser;

    public PostIt(Content content, eCourseSystemUser ownUser) {
        this.content = content;
        this.ownUser = ownUser;
    }

    public PostIt() {

    }

    public boolean isBelowOr20AndAboveOr1(int num) {
        return (num <=20 && num >= 1);
    }

    public boolean isBelowOr10AndAboveOr1(int num) {
        return (num <=10 && num >= 1);
    }

    public Content getContent() {
        return content;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public Long identity() {
        return null;
    }


    @Override
    public String toString() {
        return "PostIt[ " +
                "id = " + id +
                ", content = " + content +
                " ]";
    }

}
