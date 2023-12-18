package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.EnrollmentManagment.domain.EnrollmentID;
import ecourse.base.EnrollmentManagment.domain.EnrollmentStatus;
import ecourse.base.EnrollmentManagment.repositories.EnrollmentRepository;
import ecourse.base.EnrollmentManagment.domain.Enrollment;
import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.Course.Course;

import java.util.*;

public class JpaEnrollmentRepository extends JpaAutoTxRepository<Enrollment, Long,Long> implements EnrollmentRepository {

    public JpaEnrollmentRepository( final TransactionalContext autoTx) {
        super(autoTx, "id");
    }

    public JpaEnrollmentRepository(final  String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(), "id");
    }

    @Override
    public List<Enrollment> findAll(Course course) {
        return match("e.course = :course", "course", course);
    } //not Done properly

    @Override
    public List<Enrollment> findAllStudentEnrollments(eCourseSystemUser student) {
        final Map<String, Object> params = new HashMap<>();
        params.put("studentID", student.studentMecanographicNumber());
        return match("e.studentID = :studentID", params);
    }


    @Override
    public List<Enrollment> findAllOpenEnrollments() {
        Iterable<Enrollment> iterable = findAll();
        List<Enrollment> enrollments = new ArrayList<>();
        for (Enrollment enrollment : iterable) {
            if (enrollment.status().equals(EnrollmentStatus.OPEN)) {
                enrollments.add(enrollment);
            }
        }
        return enrollments;
    }
}
