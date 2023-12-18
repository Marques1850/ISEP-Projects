package ecourse.base.usermanagement.application;

import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.EnrollmentManagment.domain.Enrollment;
import ecourse.base.EnrollmentManagment.domain.EnrollmentStatus;

import java.util.Map;

public class EnrollmentBuilder {

    private String id;
    private Course course;
    private EnrollmentStatus status;
    private String studentID;

    public Enrollment build() {
        Enrollment enrollment = new Enrollment( course, studentID);
        return enrollment;
    }

    public EnrollmentBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public EnrollmentBuilder withCourse(Course course) {
        this.course = course;
        return this;
    }

    public EnrollmentBuilder withStatus(EnrollmentStatus status) {
        this.status = status;
        return this;
    }
}
