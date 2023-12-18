package ecourse.base.ExamMagnament.Controllers;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourse.base.ExamMagnament.FormativeExamDto;
import ecourse.base.ExamMagnament.domain.ExamCode;
import ecourse.base.ExamMagnament.domain.FormativeExam;
import ecourse.base.ExamMagnament.repositories.FormativeExamRepository;
import ecourse.base.ExamMagnament.services.TakeExamService;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

import java.util.ArrayList;
import java.util.List;

import static ecourse.base.ExamMagnament.Mapper.FormativeExamMapper.toDTO;

public class TakeFormativeExamController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final eCourseUserRepository repo = PersistenceContext.repositories().eCourseUsers();
    private final FormativeExamRepository repoE = PersistenceContext.repositories().formativeexams();

    private final TakeExamService service = new TakeExamService();

    public List<FormativeExamDto> getTakeableFormativeExams() {
        SystemUser user = getUser();
        List<FormativeExam> exams = service.getFormativeExams(user,repoE,repo);
        List<FormativeExamDto> examDto=new ArrayList<>();
        for ( FormativeExam exam : exams )  {
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

    public void takeFormativeExam(String examCode) {
        FormativeExam exam = repoE.findByCode(ExamCode.valueOf(examCode));
        service.takeFormativeExam(exam);
        repoE.save(exam);
    }
}
