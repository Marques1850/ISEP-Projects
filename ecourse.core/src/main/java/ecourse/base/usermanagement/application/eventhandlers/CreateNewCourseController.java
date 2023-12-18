package ecourse.base.usermanagement.application.eventhandlers;

import eapli.framework.general.domain.model.EmailAddress;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.application.CourseBuilder;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseDto;
import ecourse.base.usermanagement.domain.Course.courseMapper;

public class CreateNewCourseController {

    private final CourseRepository courseRepository = PersistenceContext.repositories().courses();

    private final eCourseUserRepository userRepository = PersistenceContext.repositories().eCourseUsers();

    CourseBuilder cB = new CourseBuilder();

    courseMapper mapper = new courseMapper();

    Course course;

    eCourseSystemUser regent;


    public boolean verifyCourse(String code) {
        return courseRepository.verifyCourse(code);

    }


    public boolean existsTeacher(String regentTeacher) {
        if(userRepository.searchUser(EmailAddress.valueOf(regentTeacher) ).isPresent()){
            this.regent = userRepository.searchUser(EmailAddress.valueOf(regentTeacher)).get();
            return true;
        } else {
            this.regent = null;
            return false;
        }
    }

    public CourseDto createCourse(String code, String name, String minStudents, String maxStudents, String description, String regentTeacher) {
        cB.withCode(code);
        cB.withName(name);
        cB.withMinStudents(minStudents);
        cB.withMaxStudents(maxStudents);
        cB.withDescription(description);
        cB.withRegent(this.regent);

        this.course = cB.build();

        CourseDto dto = mapper.CoursetoDto(course);

        return dto;
    }

    public boolean addCourse() {
        courseRepository.save(course);
        return true;
    }
}
