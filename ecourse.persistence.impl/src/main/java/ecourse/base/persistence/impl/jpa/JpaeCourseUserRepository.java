package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.eCourseSystemUser.eCourseSystemUserMapper;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;

import java.util.*;

public class JpaeCourseUserRepository extends JpaAutoTxRepository<eCourseSystemUser, EmailAddress, EmailAddress>
        implements eCourseUserRepository {


    public JpaeCourseUserRepository(final  String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(), "email");
    }
    public JpaeCourseUserRepository(final TransactionalContext autoTx) {
        super(autoTx, "email");
    }


    @Override
    public Optional<eCourseSystemUser> searchUser (EmailAddress id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("Email", id);
        return matchOne("email=:Email", params);
    }
    @Override
    public Optional<eCourseSystemUser> findByMeca (MecanographicNumber meca) {
        final Map<String, Object> params = new HashMap<>();
        params.put("MecanographicNumber", meca);
        return matchOne(" mecanographicNumber=:MecanographicNumber", params);
    }


    @Override
    public eCourseSystemUser alterUserStatus(eCourseSystemUserDto user) {
        final Map<String, Object> params = new HashMap<>();
        eCourseSystemUser euser = this.searchUser(EmailAddress.valueOf(user.getEmail())).get();
        euser.alterUserStatus();
        return euser;
    }
    @Override
    public int totalNumberOfUsers() {
        return (int) count();
    }
    @Override
    public List<eCourseSystemUser> listSystemUsers() {
        Iterable<eCourseSystemUser> iterable = findAll();
        List<eCourseSystemUser> users = new ArrayList<>();
        for (eCourseSystemUser user : iterable) {
            users.add(user);
        }
        return users;
    }

    @Override
    public Optional<eCourseSystemUser> findByAcronym(Acronym acronym) {
        final Map<String, Object> params = new HashMap<>();
        params.put("Acronym", acronym);
        return matchOne("acronym=:Acronym", params);
    }
/*
    public eCourseSystemUser save(eCourseSystemUser user){

    }

 */

}
