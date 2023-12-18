package ecourse.base.usermanagement.domain.Course;

public class courseMapper {
    public static CourseDto CoursetoDto(Course course) {
        return new CourseDto(String.valueOf(course.code()),course.name(),course.Status().toString(),
                course.regent().toString(), String.valueOf(course.minStudents()),
                String.valueOf(course.maxStudents()),String.valueOf(course.numStudents()), course.description());
    }

}
