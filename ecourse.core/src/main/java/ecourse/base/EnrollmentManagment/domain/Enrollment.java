package ecourse.base.EnrollmentManagment.domain;

import eapli.framework.domain.model.AggregateRoot;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.usermanagement.domain.Course.Course;

import javax.persistence.*;

import static ecourse.base.EnrollmentManagment.domain.EnrollmentStatus.OPEN;

@Entity
@Embeddable
public class Enrollment implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="STUDENT")
    private MecanographicNumber studentID;
    @OneToOne
    private Course course;
    @Column(name="Status")
    private EnrollmentStatus status;

    public Enrollment( Course course, String studentID) {
        this.course = course;
        this.studentID = MecanographicNumber.valueOf(studentID);
        this.status=OPEN; // enrollment is open by default
    }
    public Enrollment(){

    }

    public Course course(){
        return this.course;
    }
    public EnrollmentStatus status(){
        return this.status;
    }
    public MecanographicNumber studentID(){
        return this.studentID;
    }

    public void alterEnrollmentStatus(EnrollmentStatus status) {
        this.status = status;
    }

    @Override
    public boolean sameAs(Object other){
        if(!(other instanceof Enrollment)){
            return false;
        }
        final Enrollment that = (Enrollment) other;
        if(this == that){
            return true;
        }
        return course.sameAs(that.course) && studentID.equals(that.studentID);
    }

    @Override
    public Long identity() {
        return this.id;
    }

    @Override
    public boolean hasIdentity(Long id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
