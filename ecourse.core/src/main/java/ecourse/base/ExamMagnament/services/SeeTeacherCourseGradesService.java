package ecourse.base.ExamMagnament.services;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.ExamMagnament.services.ListExamGradesCourseService;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.Course;

import java.util.List;

public class SeeTeacherCourseGradesService {

    private final ListExamGradesCourseService listExamGradesCourseService = new ListExamGradesCourseService();

    public List<ExamGrade> searchUser(SystemUser user, ExamRepository repoE, eCourseUserRepository repo, CourseRepository repoC) {
        eCourseSystemUser eUser= findUser(user,repo);
        Course course= repoC.isRegent(eUser);
        return listExamGradesCourseService.findExamGrades(course,repoE);
    }

    public eCourseSystemUser findUser(SystemUser user, eCourseUserRepository repo ){
        if (repo.searchUser(user.email()).isPresent()) {
            return repo.searchUser(user.email()).get();
        } else {
            return null;
        }
    }

}
