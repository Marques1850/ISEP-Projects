package ecourse.base.EnrollmentManagment;

import ecourse.base.EnrollmentManagment.domain.Enrollment;
import ecourse.base.EnrollmentManagment.repositories.EnrollmentRepository;
import ecourse.base.usermanagement.domain.Course.Course;

public class RequestEnrollStudentService {
    public boolean requestEnrollStudent( Course course,String mecanographic, EnrollmentRepository repoEnroll) {
        Enrollment enrollment = new Enrollment(course, mecanographic);
        repoEnroll.save(enrollment);
        return true;
    }
}
