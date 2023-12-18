package ecourse.base.courseManagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.Course.CourseDto;
import ecourse.base.usermanagement.domain.Course.CourseStatus;

import java.util.List;
import java.util.Optional;

public interface  CourseRepository extends DomainRepository<CourseCode, Course> {
    List<Course> findAllCourses();
    Optional<Course> findByCode(CourseCode code);
    void removeCourse(Course Course);

    /**
     * ?? SUGESTÃO:
     * - findAllOpen() -> returns all courses that are open for enrollment
     * - findAllClosed() -> returns all courses that are closed for enrollment
     *
     * Fazer: findAllWithStatus(CourseStatus status)
     * - findAllOpen() ->  findAllWithStatus( CourseStatus.OPEN )
     * - findAllClosed() -> findAllWithStatus( CourseStatus.CLOSED )
     * ...
     * @return
     */
    List<Course> findAllWithStatus(CourseStatus status);
    List<Course> findAllOpen();
    List<Course> findAllClosed();

    boolean verifyCourse(String code);

    CourseDto VerifyCourseIsActive(String code);

    CourseDto VerifyCourseIsActiveAndEnrollment(String code);

    CourseDto enableenrollments(String id);

    CourseDto disableenrollments(String id);

    public Course isRegent(eCourseSystemUser user);
}
