/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ecourse.base.usermanagement.UserRegistration;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.*;
import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.eCourseSystemUser.eCourseSystemUserMapper;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import ecourse.base.usermanagement.domain.BaseRoles;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by Sérgio Marques on 01/05/23.
 */
@UseCaseController
public class RegisterNewUserController {
    StudentBuilder sb = new StudentBuilder();
    TeacherBuilder tb = new TeacherBuilder();
    ManagerBuilder mb = new ManagerBuilder();

    eCourseUserRepository userRep = PersistenceContext.repositories().eCourseUsers();
    CourseRepository courseRep = PersistenceContext.repositories().courses();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();

    eCourseSystemUserMapper userMapper = new eCourseSystemUserMapper();



    private eCourseSystemUser user;

    //private final UserRepository userRep = NULL;
    //private eCourseSystemUser user;

    /**
     * Get existing RoleTypes available to the user.
     *
     * @return a list of RoleTypes
     */

    BaseRoles roles = new BaseRoles();
    BaseRolesDto dto = BaseRolesMapper.toDto(roles);
    public RoleDto[] getRoleTypes() {
        Role[] roles = BaseRoles.nonUserValues();
        RoleDto[] roleDtos = new RoleDto[roles.length];
        for (int i = 0; i < roles.length; i++) {
            roleDtos[i] = RoleMapper.toDto(roles[i]);
        }
        return roleDtos;
    }


    public boolean addeUser( String password) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.MANAGER);
        user.systemUser().roleTypes().add(BaseRoles.MANAGER);
        userRep.save(this.user);
        return true;
    }
    public boolean addStudent( String password) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.MANAGER);
        user.systemUser().roleTypes().add(BaseRoles.STUDENT);
        if(user.studentMecanographicNumber().equals(new MecanographicNumber("000000001"))){
            user.setStudentCourse(courseRep.findByCode(CourseCode.valueOf("RCOMP")).get());
            user.setStudentCourse(courseRep.findByCode(CourseCode.valueOf("EAPLI")).get());
        }
        userRep.save(this.user);
        return true;
    }

    public boolean addTeacher( String password) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.ADMIN, BaseRoles.MANAGER);
        user.systemUser().roleTypes().add(BaseRoles.TEACHER);
        userRep.save(this.user);
        return true;
    }

    public eCourseSystemUserDto createStudent(final String firstName, final String lastName, final String shortName, final String email,
                                              final String password, final String mecanographicNumber, final String nif,
                                              final String birthDate){

        SystemUserBuilder systemUserBuilder2 = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        systemUserBuilder2.withUsername(shortName);
        systemUserBuilder2.withPassword(password);
        systemUserBuilder2.withEmail(email);
        systemUserBuilder2.withName(firstName, lastName);
        systemUserBuilder2.withRole(new RoleAssignment(BaseRoles.STUDENT));
        SystemUser systemUser = systemUserBuilder2.build();

        sb.withSystemUser(systemUser);
        sb.withEmail(email);
        sb.withMecanographicNumber(mecanographicNumber);
        sb.withNIF(nif);
        sb.withBirthDate(birthDate);

        this.user = sb.build();

        return userMapper.toDto(user);
    }

    public eCourseSystemUserDto createTeacher(final String firstName, final String lastName, final String shortName, final String email,
                                           final String password, final String acronym, final String nif,
                                           final String birthDate){

        SystemUserBuilder systemUserBuilder = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        systemUserBuilder.withUsername(shortName);
        systemUserBuilder.withPassword(password);
        systemUserBuilder.withEmail(email);
        systemUserBuilder.withName(firstName, lastName);
        systemUserBuilder.withRole(new RoleAssignment(BaseRoles.TEACHER));
        SystemUser systemUser = systemUserBuilder.build();

        tb.withSystemUser(systemUser);
        tb.withEmail(email);
        tb.withAcronym(acronym);
        tb.withNIF(nif);
        tb.withBirthDate(birthDate);

        this.user = tb.build();

        return userMapper.toDto(user);
    }

    public eCourseSystemUserDto createManager(final String firstName, final String lastName, final String shortName, final String email,
                                 final String password, final String nif, final String birthDate){

        SystemUserBuilder systemUserBuilder = new SystemUserBuilder(new BasePasswordPolicy(), new PlainTextEncoder());
        systemUserBuilder.withUsername(shortName);
        systemUserBuilder.withPassword(password);
        systemUserBuilder.withEmail(email);
        systemUserBuilder.withName(firstName, lastName);
        systemUserBuilder.withRole(new RoleAssignment(BaseRoles.MANAGER));
        SystemUser systemUser = systemUserBuilder.build();

        mb.withSystemUser(systemUser);
        mb.withEmail(email);
        mb.withNIF(nif);
        mb.withBirthDate(birthDate);

        this.user = mb.build();

        return userMapper.toDto(user);
    }

    public eCourseSystemUser nowUser(){
        return this.user;
    }
}
