package ecourse.base.usermanagement.UserRegistration;


import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Username;
import ecourse.base.clientusermanagement.domain.ClientUser;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;

import java.util.List;
import java.util.Optional;

public interface eCourseUserRepository extends DomainRepository<EmailAddress, eCourseSystemUser> {

    Optional<eCourseSystemUser> searchUser (EmailAddress id);

    Optional<eCourseSystemUser> findByMeca (MecanographicNumber meca);

    long count();

    eCourseSystemUser alterUserStatus(eCourseSystemUserDto user);

    int totalNumberOfUsers();

    List<eCourseSystemUser> listSystemUsers();
    Optional<eCourseSystemUser> findByAcronym(Acronym acronym);

}

