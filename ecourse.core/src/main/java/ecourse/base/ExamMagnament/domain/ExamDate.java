package ecourse.base.ExamMagnament.domain;

import javax.persistence.Embeddable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class ExamDate {

    public Date date;

    public ExamDate(Date date){
        this.date=date;
    }

    public ExamDate() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamDate)) return false;
        ExamDate examDate = (ExamDate) o;
        return Objects.equals(date, examDate.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return "ExamDate{" +
                "date=" + date +
                '}';
    }
}
