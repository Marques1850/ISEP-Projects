package ecourse.base.ExamMagnament.domain;

import eapli.framework.domain.model.ValueObject;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ExamGrade {
    @OneToOne
    private eCourseSystemUser student;
    private float grade;

    public ExamGrade(eCourseSystemUser student, float grade) {
        this.student = student;
        this.grade = grade;
    }

    public ExamGrade(eCourseSystemUser student) {
        this.student = student;
    }

    public ExamGrade() {

    }

    public boolean grade(float grade) {
        if(grade >= 0 && grade <= 20){
            this.grade = grade;
            return true;
        }
        return false;
    }

    public eCourseSystemUser student() {
        return student;
    }

    public float grade() {
        return grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamGrade examGrade = (ExamGrade) o;
        return Float.compare(examGrade.grade, grade) == 0 && Objects.equals(student, examGrade.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, grade);
    }
}
