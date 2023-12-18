package ecourse.base.courseManagement;


import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class ListCoursesServiceTest {
    private static CourseRepository repo ;
    private ListCoursesService service;
    private static eCourseUserRepository userRepo;

    @BeforeAll
    static void setup() {
        repo = mock(CourseRepository.class);
        userRepo = mock(eCourseUserRepository.class);
        repo.save(new Course( "Test1", CourseCode.valueOf("TEST1")));
        repo.save(new Course( "Test2", CourseCode.valueOf("TEST2")));
        repo.save(new Course( "Test3", CourseCode.valueOf("TEST3")));

    }

    @Test
    void testListCoursesForNonExistingUser() {

    }

    @Test
    void testListCoursesForManager() {

    }

    @Test
    void testListCoursesForTeacher() {

    }

    @Test
    void testListCoursesForStudent() {

    }
}