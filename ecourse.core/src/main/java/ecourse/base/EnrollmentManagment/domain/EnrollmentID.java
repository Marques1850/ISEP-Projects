package ecourse.base.EnrollmentManagment.domain;

import eapli.framework.domain.model.ValueObject;

import java.util.Objects;

public class EnrollmentID implements ValueObject,Comparable<EnrollmentID> {
    private final String id;

    public EnrollmentID(String id) {
        this.id = id;
    }
    public EnrollmentID() {
        this.id = null;
    }

    public static EnrollmentID valueOf(String id) {
        return new EnrollmentID(id);
    }

public String id() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrollmentID)) return false;
        EnrollmentID that = (EnrollmentID) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EnrollmentID{" +
                "id='" + id + '\'' +
                '}';
    }
    @Override
    public int compareTo(EnrollmentID o) {
        if(o.id.equals(this.id)) return 1;
        return 0;
    }
}
