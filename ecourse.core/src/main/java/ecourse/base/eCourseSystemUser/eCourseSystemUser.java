package ecourse.base.eCourseSystemUser;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.*;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.usermanagement.domain.BaseRoles;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.Grade;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Entity
public class eCourseSystemUser implements AggregateRoot<EmailAddress>, Serializable {


    @EmbeddedId
    EmailAddress email;
    @Column(unique = true, name="USER_NIF")
    private NIF nif;
    @Column(name="USER_BIRTHDATE")
    private BirthDate birthDate;
    @Column(name="USER_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;
    @Column(name="USER_ROLE")
    private Role role;
    @Column(name="STUDENT_GRADE")
    private Grade grade;
    @Column(name="STUDENT_MECHANOGRAPHICNUMBER", unique = true)
    private MecanographicNumber mecanographicNumber;
    @OneToMany
    @JoinColumn(name="COURSES_TEACHED")
    private List<Course> coursesLearning;
    @Column(name="TEACHER_ACRONYM", unique = true)
    private Acronym acronym;
    @OneToMany
    @JoinColumn(name="COURSES_TEACHING")
    private List<Course> coursesTeaching;
    @OneToOne(cascade = {CascadeType.MERGE})
    private SystemUser systemUser;

    public eCourseSystemUser(EmailAddress email, SystemUser systemUser, NIF nif, BirthDate birthDate) {
        this.email = email;
        this.systemUser = systemUser;
        this.nif = nif;
        this.birthDate = birthDate;
        this.status = UserStatus.ENABLE;
        this.role = BaseRoles.MANAGER;

    }

    public eCourseSystemUser(EmailAddress email, SystemUser systemUser, MecanographicNumber mecanographicNumber, NIF nif, BirthDate birthDate) {
        this.email = email;
        this.systemUser = systemUser;
        this.nif = nif;
        this.mecanographicNumber = mecanographicNumber;
        this.birthDate = birthDate;
        this.status = UserStatus.ENABLE;
        this.role = BaseRoles.STUDENT;
        this.coursesLearning = new ArrayList<>();
    }

    public eCourseSystemUser(EmailAddress email, SystemUser systemUser, Acronym acronym, NIF nif, BirthDate birthDate) {
        this.email = email;
        this.systemUser = systemUser;
        this.nif = nif;
        this.acronym = acronym;
        this.birthDate = birthDate;
        this.status = UserStatus.ENABLE;
        this.role = BaseRoles.TEACHER;
        this.coursesTeaching = new ArrayList<>();
    }


    public eCourseSystemUser() {

    }

    public SystemUser systemUser() {
        return this.systemUser;
    }

    public EmailAddress eCourseUserEmail() {
        return this.email;
    }

    public NIF eCourseUserNIF() {
        return this.nif;
    }

    public BirthDate eCourseUserBirthDate() {
        return this.birthDate;
    }

    public UserStatus eCourseUserStatus() {
        return this.status;
    }

    public Acronym teacherAcronym(){
        return this.acronym;
    }
    public List<Course> coursesLearning(){
        return this.coursesLearning;
    }
    public List<Course> coursesTeaching(){
        return this.coursesTeaching;
    }
    public MecanographicNumber studentMecanographicNumber() {
        return this.mecanographicNumber;
    }

    public Role eCourseUserRole() {
        return this.role;
    }

    public String systemUserUsername() {
        return this.systemUser.username().toString();
    }

    public void enableUser(){
        this.status = UserStatus.ENABLE;
    }
    public void disableUser(){
        this.status = UserStatus.DISABLE;
    }

    @Override
    public boolean sameAs(Object other) {
        return this.email.toString().equals(((eCourseSystemUser)other).email.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof eCourseSystemUser)) return false;
        eCourseSystemUser that = (eCourseSystemUser) o;
        return Objects.equals(email, that.email) && Objects.equals(systemUser, that.systemUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, systemUser);
    }

    @Override
    public EmailAddress identity() {
        return this.email;
    }


    public void alterUserStatus(){
        if(this.status == UserStatus.ENABLE){
            this.status = UserStatus.DISABLE;
            Calendar calendar = Calendar.getInstance();
            this.systemUser.deactivate(calendar);
        }else{
            this.status = UserStatus.ENABLE;
            this.systemUser.activate();
        }
    }

    public boolean setTeacherCourse(Course course){
        if(this.role.equals(Role.valueOf("TEACHER"))){
            for (Course c : this.coursesTeaching){
                if(c.code().equals(course.code())){
                    return false;
                }
            }

            this.coursesTeaching.add(course);
            return true;
        }
        System.out.println("User is not a teacher");
        return false;
    }

    public boolean setStudentCourse(Course course){
        if(this.role.equals(Role.valueOf("STUDENT"))){
            if(this.coursesLearning.contains(course)){
                return false;
            }
            this.coursesLearning.add(course);
            return true;
        }
        throw new IllegalStateException("User is not a student");
    }

    @Override
    public String toString() {
        return "eCourseSystemUser[ " +
                "email=" + email +
                " | nif=" + nif +
                " | birthDate=" + birthDate +
                " | status=" + status +
                " | role=" + role +
                " | grade=" + grade +
                " | mecanographicNumber=" + mecanographicNumber +
                " | coursesLearning=" + coursesLearning +
                " | acronym=" + acronym +
                " | coursesTeaching=" + coursesTeaching +
                " | systemUser=" + systemUser +
                " ]";
    }
}
