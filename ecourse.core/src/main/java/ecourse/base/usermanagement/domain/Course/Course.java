package ecourse.base.usermanagement.domain.Course;


import eapli.framework.domain.model.AggregateRoot;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Course implements AggregateRoot<CourseCode> {
    @EmbeddedId
    CourseCode code;
    @Column(name="COURSE_NAME", length = 50, nullable = false, unique = false)
    private String name;
    @Column(name="COURSE_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private CourseStatus status;
    @OneToOne
    private eCourseSystemUser regent;
    @Column(name="MIN_STUDENTS")

    private int minStudents;
    @Column(name="MAX_STUDENTS")
    private int maxStudents;
    @Column(name="STUDENTS_IN_COURSE")
    private int numStudents;
    @Column(name="COURSE_DESCRIPTION")
    private String description;


    public Course (CourseCode code, String name, eCourseSystemUser regent, int minStudents, int maxStudents, String description){
        this.code=code;
        this.name=name;
        this.regent=regent;
        this.minStudents=minStudents;
        this.maxStudents=maxStudents;
        this.numStudents=0;
        this.description=description;
        this.status=CourseStatus.CLOSED;
    }

    public Course(String name, CourseCode code) {
        this.name = name;
        this.status = CourseStatus.OPEN;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return minStudents == course.minStudents && maxStudents == course.maxStudents && numStudents == course.numStudents && Objects.equals(code, course.code) && Objects.equals(name, course.name) && status == course.status && Objects.equals(regent, course.regent) && Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, status, regent, minStudents, maxStudents, numStudents, description);
    }

    public Course() {
    }
    public String name() {
        return this.name;
    }
    public CourseCode code() {
        return this.code;
    }
    public CourseStatus Status() {
        return status;
    }

    public eCourseSystemUser regent() {
        return regent;
    }
    public int minStudents() {
        return minStudents;
    }
    public int maxStudents() {
        return maxStudents;
    }
    public int numStudents() {
        return numStudents;
    }
    public void numStudents(int numStudents) {
        this.numStudents = numStudents;
    }
    public void CloseCourse(){
        status=CourseStatus.CLOSED;
    }
    public void OpenCourse(){
        status=CourseStatus.OPEN;
    }

    public String description() {
        return description;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public CourseCode identity() {
        return this.code;
    }

    public void openCourse() {
        this.status = CourseStatus.OPEN;
    }

    public void closeCourse() {
        this.status = CourseStatus.CLOSED;
    }

    public void enableEnrollments() {
        this.status = CourseStatus.OPEN_ENROLLMENT;
    }

    public void disableEnrollments() {
        this.status = CourseStatus.OPEN;
    }
}
