package ecourse.base.usermanagement.application;



import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.eCourseSystemUser.eCourseSystemUserMapper;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.Course.CourseStatus;

import javax.persistence.Column;
import javax.persistence.Id;

public class CourseBuilder {

    private CourseCode code;

    private String name;

    private CourseStatus status;

    private eCourseSystemUser regent;

    private int minStudents;

    private int maxStudents;

    private int numStudents;

    private String description;


    public Course build() {
        Course course = new Course(this.code, this.name, this.regent, this.minStudents, this.maxStudents, this.description);
        return course;
    }

    public CourseBuilder withCode(String code) {
        this.code = CourseCode.valueOf(code);
        return this;
    }

    public CourseBuilder withCode(CourseCode code) {
        this.code = code;
        return this;
    }

    public CourseBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CourseBuilder withRegent(eCourseSystemUser regent) {
        this.regent = regent;
        return this;
    }

    public CourseBuilder withMinStudents(String minStudents) {
        this.minStudents = Integer.parseInt(minStudents);
        return this;
    }

    public CourseBuilder withMinStudents(int minStudents) {
        this.minStudents = minStudents;
        return this;
    }

    public CourseBuilder withMaxStudents(String maxStudents) {
        this.maxStudents = Integer.parseInt(maxStudents);
        return this;
    }

    public CourseBuilder withMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
        return this;
    }

    public CourseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }
}
