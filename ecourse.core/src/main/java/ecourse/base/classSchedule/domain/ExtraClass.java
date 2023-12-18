package ecourse.base.classSchedule.domain;

import eapli.framework.domain.model.AggregateRoot;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.StudentBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Embeddable
public class ExtraClass implements AggregateRoot<ClassTitle> {

    @Id
    @Column(name="CLASS_TITLE",unique = true)
    private ClassTitle title;
    @Column(name="CLASS_DURATION")
    private ClassDuration duration;
    @Column(name="CLASS_DATE")
    private LocalDateTime date;
    @OneToMany
    @Column(name="STUDENT_LIST")
    private List<eCourseSystemUser> studentList;

    public ExtraClass(String title, int duration, LocalDateTime date, List<eCourseSystemUser> studentList) {
        this.title = ClassTitle.valueOf(title);
        this.duration = ClassDuration.valueOf(duration);
        this.date = date;
        this.studentList = studentList;
    }

    public ExtraClass() {}

    @Override
    public boolean sameAs(Object other) {return false;}
    @Override
    public ClassTitle identity() {return null;}

    public ClassTitle title() {return title;}
    public ClassDuration duration() {return duration;}
    public LocalDateTime date() {return date;}
    public List<eCourseSystemUser> studentList() {return studentList;}


}

