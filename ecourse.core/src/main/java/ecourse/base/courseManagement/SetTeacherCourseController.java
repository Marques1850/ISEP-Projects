package ecourse.base.courseManagement;

import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.eCourseSystemUser.TeacherMapper;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.Course.CourseDto;
import ecourse.base.usermanagement.domain.Course.courseMapper;
import ecourse.base.usermanagement.domain.FundamentalClasses.Acronym;

import java.util.ArrayList;
import java.util.List;

public class SetTeacherCourseController {

    private final CourseRepository repo = PersistenceContext.repositories().courses();
    private final eCourseUserRepository repoU = PersistenceContext.repositories().eCourseUsers();
   public List<CourseDto> getCourses() {
      List<Course> courses=repo.findAllCourses();
       List<CourseDto> coursesDto = new ArrayList<>();
       for (Course course : courses) {
           coursesDto.add(ecourse.base.usermanagement.domain.Course.courseMapper.CoursetoDto(course));
       }
     return coursesDto;
    }

    public List<eCourseSystemUserDto> getTeachers() {
        Iterable<eCourseSystemUser> Teachers=repoU.findAll();
        List<eCourseSystemUserDto> TeacherDto = new ArrayList<>();
        TeacherMapper teacherMapper =new TeacherMapper();
        for (eCourseSystemUser teacher : Teachers) {
            if(teacher.teacherAcronym()!=null) TeacherDto.add(teacherMapper.toTeacherDTO(teacher));

        }
        return TeacherDto;
    }

    public boolean  setTeachersCourse(String courseCode, List<Acronym> acronyms) {

        Course course= repo.findByCode(CourseCode.valueOf(courseCode)).get();
        List<eCourseSystemUser> teachers=new ArrayList<>();
        for (Acronym acronym : acronyms) {
            if(repoU.findByAcronym(acronym).isPresent()) {
                eCourseSystemUser teacher = repoU.findByAcronym(acronym).get();
                teachers.add(teacher);
            }
        }

      List<eCourseSystemUser> Tteachers =TeachersinCourseService.setTeachersCourse(course,teachers);
        add(Tteachers);

        return true;
    }

    public void add(List<eCourseSystemUser> teachers){
        for ( eCourseSystemUser teacher : teachers) {
            repoU.save(teacher);
        }
    }

}
