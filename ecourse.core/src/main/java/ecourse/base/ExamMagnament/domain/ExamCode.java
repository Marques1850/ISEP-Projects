package ecourse.base.ExamMagnament.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;
@Embeddable
public class ExamCode implements ValueObject, Comparable<ExamCode> {

    private final String ExamCode;

    public ExamCode(String examCode) {
        ExamCode = examCode;
    }

    public ExamCode() {
        ExamCode = null;
    }


    public static ExamCode valueOf(String examCode) {

        return new ExamCode(examCode);
    }




    public String  Examcode() {
        return this.ExamCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamCode)) return false;
        ExamCode examCode = (ExamCode) o;
        return Objects.equals(ExamCode, examCode.ExamCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ExamCode);
    }

    @Override
    public String toString() {
        return "ExamCode{" +
                "ExamCode=" + ExamCode +
                '}';
    }

    @Override
    public int compareTo(ecourse.base.ExamMagnament.domain.ExamCode o) {
        return 0;
    }
}
