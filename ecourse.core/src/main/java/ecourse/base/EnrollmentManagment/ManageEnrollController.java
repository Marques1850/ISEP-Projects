package ecourse.base.EnrollmentManagment;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.EnrollmentManagment.domain.Enrollment;
import ecourse.base.usermanagement.domain.BaseRoles;

import java.util.List;

public class ManageEnrollController {

    private final ManageEnrollService service = new ManageEnrollService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();


    public List<Enrollment> getOpenEnrollments() {
        verifyUserAuthorization();
        return service.getOpenEnrollments();
    }

    public void acceptEnrollment(Enrollment enrollment) {
        verifyUserAuthorization();
        service.acceptEnrollment(enrollment);
    }

    public void rejectEnrollment(Enrollment enrollment) {
        verifyUserAuthorization();
        service.rejectEnrollment(enrollment);
    }

    private void verifyUserAuthorization(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.MANAGER);
    }
}
