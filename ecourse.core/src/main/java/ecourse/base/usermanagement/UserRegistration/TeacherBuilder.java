package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.*;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import ecourse.base.eCourseSystemUser.UserStatus;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;

import java.util.List;

public class TeacherBuilder {

    private EmailAddress email;
    private UserStatus status;
    private List<Course> coursesTeached;
    private Acronym acronym;
    private NIF nif;
    private BirthDate birthDate;

    private SystemUser systemUser;

    public eCourseSystemUser build() {
        eCourseSystemUser eCourseSystemUser = new eCourseSystemUser(this.email, this.systemUser, this.acronym, this.nif, this.birthDate);
        return eCourseSystemUser;
    }

    public TeacherBuilder withSystemUser(final SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    public TeacherBuilder withEmail(final String email) {
        this.email = EmailAddress.valueOf(email);
        return this;
    }

    public TeacherBuilder withEmail(EmailAddress email) {
        this.email = email;
        return this;
    }

    public TeacherBuilder withNIF(final String nif) {
        this.nif = NIF.valueOf(nif);
        return this;
    }

    public TeacherBuilder withNIF(NIF nif) {
        this.nif = nif;
        return this;
    }

    public TeacherBuilder withBirthDate(final String birthDate) {
        this.birthDate = BirthDate.valueOf(birthDate);
        return this;
    }

    public TeacherBuilder withBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public TeacherBuilder withAcronym(final String acronym) {
        this.acronym = Acronym.valueOf(acronym);
        return this;
    }

    public TeacherBuilder withAcronym(Acronym acronym) {
        this.acronym = acronym;
        return this;
    }

}
