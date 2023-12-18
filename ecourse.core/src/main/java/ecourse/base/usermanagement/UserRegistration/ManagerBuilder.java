package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.*;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.FundamentalClasses.BirthDate;
import ecourse.base.usermanagement.domain.FundamentalClasses.NIF;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManagerBuilder implements DomainFactory<eCourseSystemUser> {

    private static final Logger LOGGER = LogManager.getLogger(ManagerBuilder.class);
    private EmailAddress email;
    private SystemUser systemUser;

    private NIF nif;
    private BirthDate birthDate;

    public ManagerBuilder() {
    }

    public eCourseSystemUser build() {
        eCourseSystemUser eCourseSystemUser = new eCourseSystemUser(this.email, this.systemUser, this.nif, this.birthDate);
        return eCourseSystemUser;
    }

    public ManagerBuilder withSystemUser(final SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    public ManagerBuilder withEmail(final String email) {
        this.email = EmailAddress.valueOf(email);
        return this;
    }

    public ManagerBuilder withEmail(EmailAddress email) {
        this.email = email;
        return this;
    }

    public ManagerBuilder withNIF(final String nif) {
        this.nif = NIF.valueOf(nif);
        return this;
    }

    public ManagerBuilder withNIF(NIF nif) {
        this.nif = nif;
        return this;
    }

    public ManagerBuilder withBirthDate(final String birthDate) {
        this.birthDate = BirthDate.valueOf(birthDate);
        return this;
    }

    public ManagerBuilder withBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }
}
