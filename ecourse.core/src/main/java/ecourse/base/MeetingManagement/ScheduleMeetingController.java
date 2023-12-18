package ecourse.base.MeetingManagement;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleMeetingController {
    private final ScheduleMeetingService service;
    private final AuthorizationService authorizationService;
    private final eCourseUserRepository userRepo;

    public ScheduleMeetingController() {
        this.authorizationService = AuthzRegistry.authorizationService();
        this.userRepo = PersistenceContext.repositories().eCourseUsers();
        this.service = new ScheduleMeetingService(
                userRepo,
                PersistenceContext.repositories().meetings()
        );

    }
    public Meeting scheduleMeeting( LocalDate date, LocalTime time, int duration, List<EmailAddress> participants) {
        EmailAddress email = authorizationService.session().get().authenticatedUser().email();
        eCourseSystemUser owner = userRepo.searchUser(email).get();
        List<eCourseSystemUser> validatedParticipants = service.validateParticipants(participants);
        return service.scheduleMeeting( date, time, duration, validatedParticipants, owner);
    }

    public boolean saveMeeting(Meeting meeting) {
        return service.saveMeeting(meeting);
    }
}