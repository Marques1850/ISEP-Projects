package ecourse.base.ExamMagnament.ListFutureExam;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.services.ListExamCoursesService;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.Course;

import java.util.List;

public class ListFutureExamsService {

    private final ListExamCoursesService listExamCoursesService = new ListExamCoursesService();

    public List<Exam> getFutureExams(SystemUser user, ExamRepository repoE, eCourseUserRepository repo){
     eCourseSystemUser eUser= findUser(user,repo);
     List<Course> courses= getStudentCourses(eUser);
     return listExamCoursesService.getFutureExams(courses,repoE);

    }
    public eCourseSystemUser findUser(SystemUser user, eCourseUserRepository repo ){
        if(repo.searchUser(user.email()).isPresent())
           return repo.searchUser(user.email()).get();
        else
            return null;

    }

    public List<Course> getStudentCourses(eCourseSystemUser user){
        return user.coursesLearning();
    }
}
