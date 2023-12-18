package ecourse.base.ExamMagnament.ListFutureExam;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.ArrayList;
import java.util.List;

import static ecourse.base.ExamMagnament.Mapper.ExamMapper.toDTO;


public class ListFutureExamsController {

    private final ListFutureExamsService service = new ListFutureExamsService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final eCourseUserRepository repo = PersistenceContext.repositories().eCourseUsers();
    private final ExamRepository repoE=PersistenceContext.repositories().exams();




    public List<ExamDto> getFutureExams() {
        SystemUser user = getUser();
        List<Exam> exams = service.getFutureExams(user,repoE,repo);
        List<ExamDto> examDto=new ArrayList<>();
        for ( Exam exam : exams )  {
            examDto.add(toDTO(exam));
        }
        return examDto;
    }

  public SystemUser getUser(){
        if(authz.session().isPresent()) {
            final UserSession session = authz.session().get();
            return session.authenticatedUser();
        }
        throw new IllegalStateException("Session without user in it.");

  }


}
