package ecourse.base.ExamMagnament.Controllers;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.ExamCode;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.ExamMagnament.services.TakeExamService;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.ArrayList;
import java.util.List;

import static ecourse.base.ExamMagnament.Mapper.ExamMapper.toDTO;

public class TakeExamController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final eCourseUserRepository repo = PersistenceContext.repositories().eCourseUsers();
    private final ExamRepository repoE=PersistenceContext.repositories().exams();

    private final TakeExamService service = new TakeExamService();

    public List<ExamDto> getTakeableExams() {
        SystemUser user = getUser();
        List<Exam> exams = service.getExams(user,repoE,repo);
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

    public void takeExam(String examCode) {
        Exam exam = repoE.findByCode(ExamCode.valueOf(examCode));
        service.takeExam(exam);
        repoE.save(exam);
    }
}
