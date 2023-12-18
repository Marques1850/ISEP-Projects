package ecourse.base.courseManagement;

import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.Course.Course;

import java.util.ArrayList;
import java.util.List;

public class TeachersinCourseService {
        private final CourseRepository repo = PersistenceContext.repositories().courses();
        public static List<eCourseSystemUser> setTeachersCourse(Course course, List<eCourseSystemUser> teachers) {
           List <eCourseSystemUser> acceptedTeachers=new ArrayList<>();
                for (eCourseSystemUser teacher : teachers) {
                        boolean result= teacher.setTeacherCourse(course);
                        if(result){
                                acceptedTeachers.add(teacher);
                        }
                }
               return acceptedTeachers;
        }


}
