package ecourse.base.courseManagement;

import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.Course.CourseDto;
import ecourse.base.usermanagement.domain.Course.courseMapper;

public class OpenCloseEnrollmentController {

    private final CourseRepository courseRep = PersistenceContext.repositories().courses();

    private final courseMapper courseMapper = new courseMapper();

    private boolean verifyCourse;

    public boolean verifyCourseExists(String id) {
        if(courseRep.verifyCourse(id) == true){
            this.verifyCourse = true;
            return true;
        } else {
            this.verifyCourse = false;
            return false;
        }
    }

    public CourseDto VerifyCourseIsActive(String code){
        return courseRep.VerifyCourseIsActive(code);
    }

    public CourseDto VerifyCourseIsActiveAndEnrollment(String code){
        return courseRep.VerifyCourseIsActiveAndEnrollment(code);
    }


    public CourseDto enableenrollments(String id) {
        return courseRep.enableenrollments(id);
    }

    public CourseDto disableenrollments(String id) {
        return courseRep.disableenrollments(id);
    }

    public void saveSatusChange(CourseDto courseenrol) {
        Course course = courseRep.findByCode(CourseCode.valueOf(courseenrol.getCode())).get();
        courseRep.save(course);
    }
}
