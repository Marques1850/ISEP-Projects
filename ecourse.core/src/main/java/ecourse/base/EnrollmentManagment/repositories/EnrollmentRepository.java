package ecourse.base.EnrollmentManagment.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import ecourse.base.EnrollmentManagment.domain.EnrollmentID;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.EnrollmentManagment.domain.Enrollment;

import java.util.List;

public interface EnrollmentRepository extends DomainRepository<Long,Enrollment> {
    List<Enrollment> findAll(Course course);
    List<Enrollment> findAllStudentEnrollments(eCourseSystemUser student);

    List<Enrollment> findAllOpenEnrollments();
}
