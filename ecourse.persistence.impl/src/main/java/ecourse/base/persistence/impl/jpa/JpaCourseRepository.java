package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.Course.*;
import ecourse.base.courseManagement.repositories.CourseRepository;


import java.util.*;

public class JpaCourseRepository
        extends JpaAutoTxRepository<Course, CourseCode, CourseCode>
        implements CourseRepository {

    public JpaCourseRepository(final  String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(), "coursecode");
    }
    public JpaCourseRepository(final TransactionalContext autoTx) {
        super(autoTx, "coursecode");
    }

    @Override
    public void removeCourse(Course Course) {
        //implement class
    }

    @Override
    public Optional<Course> findByCode(CourseCode code) {
        final Map<String, Object> params = new HashMap<>();
        params.put("Code", code);
        return matchOne("code=:Code", params);
    }

    @Override
    public List<Course> findAllCourses() {
        Iterable<Course> iterable = findAll();
        List<Course> courses = new ArrayList<>();
        for (Course course : iterable) {
            courses.add(course);
        }
        return courses;
    }

    @Override
    public List<Course> findAllWithStatus(CourseStatus status) {
        Iterable<Course> iterable = findAll();
        List<Course> courses = new ArrayList<>();
        for (Course course : iterable) {
            if (course.Status().equals(status)) courses.add(course);
        }
        return courses;
    }

    @Override
    public List<Course> findAllOpen() {
        return match("e.status=ecourse.base.usermanagement.domain.Course.CourseStatus.OPEN");
    }

    @Override
    public List<Course> findAllClosed() {
        return match("e.status=ecourse.base.usermanagement.domain.Course.CourseStatus.CLOSED");
    }

    @Override
    public boolean verifyCourse(String code) {
        final Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        return matchOne("coursecode=:code", params).isPresent();
    }

    @Override
    public CourseDto VerifyCourseIsActive(String code) {
        Course course = this.findByCode(CourseCode.valueOf(code)).get();
        if(course.Status().toString().equals("OPEN")){
            return courseMapper.CoursetoDto(course);
        } else {
            return null;
        }
    }

    @Override
    public CourseDto VerifyCourseIsActiveAndEnrollment(String code) {
        Course course = this.findByCode(CourseCode.valueOf(code)).get();
        if(course.Status().toString().equals("OPEN_ENROLLMENT")){
            return courseMapper.CoursetoDto(course);
        } else {
            return null;
        }
    }

    @Override
    public CourseDto enableenrollments(String id) {
        Course course = this.findByCode(CourseCode.valueOf(id)).get();
        course.enableEnrollments();
        return courseMapper.CoursetoDto(course);
    }

    @Override
    public CourseDto disableenrollments(String id) {
        Course course = this.findByCode(CourseCode.valueOf(id)).get();
        course.disableEnrollments();
        return courseMapper.CoursetoDto(course);
    }

    @Override
    public Course isRegent(eCourseSystemUser user) {
        Iterable<Course> iterable = findAll();
        for (Course course : iterable) {
            if (course.regent().eCourseUserEmail().toString().equals(user.eCourseUserEmail().toString())) return course;
        }
        return null;
    }
}
