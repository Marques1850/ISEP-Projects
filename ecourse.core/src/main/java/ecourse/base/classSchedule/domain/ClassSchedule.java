package ecourse.base.classSchedule.domain;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ClassSchedule implements AggregateRoot<ClassTitle> {

    @EmbeddedId
    private ClassTitle title;
    @Column(name="CLASS_DURATION")
    private int duration;
    @Column(name="CLASS_DATE")
    private LocalDateTime date;

    public ClassSchedule(String title, int duration, LocalDateTime date) {
        this.title = ClassTitle.valueOf(title);
        this.duration = duration;
        this.date = date;
    }

    public ClassSchedule() {}


    @Override
    public boolean sameAs(Object other) {return false;}

    public ClassTitle title() {return title;}

    public int duration() {return duration;}

    public LocalDateTime date() {return date;}

    @Override
    public ClassTitle identity() {return null;}

    @Override
    public String toString() {
        return "Class{" +
                "title=" + title +
                ", duration=" + duration +
                ", date=" + date +
                '}';
    }
}
