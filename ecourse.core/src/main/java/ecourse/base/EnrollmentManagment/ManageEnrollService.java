package ecourse.base.EnrollmentManagment;

import ecourse.base.EnrollmentManagment.domain.Enrollment;
import ecourse.base.EnrollmentManagment.domain.EnrollmentStatus;
import ecourse.base.EnrollmentManagment.repositories.EnrollmentRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;

import java.util.List;

public class ManageEnrollService {

    private final EnrollmentRepository repo = PersistenceContext.repositories().enrollments();
    public List<Enrollment> getOpenEnrollments() {
        List<Enrollment> enrollments = repo.findAllOpenEnrollments();
        return enrollments;
    }

    public void acceptEnrollment(Enrollment enrollment) {
        enrollment.alterEnrollmentStatus(EnrollmentStatus.ACCEPTED);
        repo.save(enrollment);
    }

    public void rejectEnrollment(Enrollment enrollment) {
        enrollment.alterEnrollmentStatus(EnrollmentStatus.REJECTED);
        repo.save(enrollment);
    }
}
