package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.*;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.Grade;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;

public class StudentBuilder {


    private SystemUser systemUser;
    private EmailAddress email;
    private Grade grade;
    private MecanographicNumber mecanographicNumber;
    private NIF nif;
    private BirthDate birthDate;

    private SystemUserBuilder systemUserBuilder = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());


    public eCourseSystemUser build() {
        eCourseSystemUser eCourseSystemUser = new eCourseSystemUser(this.email, this.systemUser,
                this.mecanographicNumber, this.nif, this.birthDate);
        return eCourseSystemUser;
    }

    public StudentBuilder withSystemUser(final SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    public StudentBuilder withEmail(final String email) {
        this.email = EmailAddress.valueOf(email);
        return this;
    }

    public StudentBuilder withEmail(EmailAddress email) {
        this.email = email;
        return this;
    }

    public StudentBuilder withNIF(final String nif) {
        this.nif = NIF.valueOf(nif);
        return this;
    }

    public StudentBuilder withNIF(NIF nif) {
        this.nif = nif;
        return this;
    }

    public StudentBuilder withBirthDate(final String birthDate) {
        this.birthDate = BirthDate.valueOf(birthDate);
        return this;
    }

    public StudentBuilder withBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public StudentBuilder withMecanographicNumber(final String mecanographicNumber) {
        this.mecanographicNumber = MecanographicNumber.valueOf(mecanographicNumber);
        return this;
    }

    public StudentBuilder withMecanographicNumber(MecanographicNumber mecanographicNumber) {
        this.mecanographicNumber = mecanographicNumber;
        return this;
    }

}
