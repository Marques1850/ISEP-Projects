package ecourse.base.EnrollmentManagment;

import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BulkEnrollController {
    private final CourseRepository repo = PersistenceContext.repositories().courses();
    private final eCourseUserRepository repoUser = PersistenceContext.repositories().eCourseUsers();
    private final BulkEnrollService service = new BulkEnrollService();

    public List<CourseDto> listCoursesEnrollmentOpen(){
        List<Course> courses = repo.findAllWithStatus(CourseStatus.OPEN_ENROLLMENT);
        List<CourseDto> coursesDto = new ArrayList<>();
        courseMapper courseMapper=new courseMapper();
        for (Course course : courses) {
            coursesDto.add(courseMapper.CoursetoDto(course));
        }
        return coursesDto;
    }

   public List<eCourseSystemUserDto> enrollStudents(String courseID, String csvPath) throws FileNotFoundException {
       Course course= repo.findByCode(CourseCode.valueOf(courseID)).get();

       return service.enrollStudents(course, csvPath,repoUser);
   }
}