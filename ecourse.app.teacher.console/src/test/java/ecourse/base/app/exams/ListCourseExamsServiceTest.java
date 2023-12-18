package ecourse.base.app.exams;

import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.usermanagement.application.CourseBuilder;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCourseExamsServiceTest {

    private static ExamRepository examRepo;
    private static CourseRepository courseRepo;
    private static ListCourseExamsService service;

    private static Course test1;
    private static Course empty;
    private static List<Exam> examList;

    @BeforeAll
    static void setup() {
        examRepo = mock(ExamRepository.class);
        courseRepo = mock(CourseRepository.class);

        CourseBuilder cB = new CourseBuilder();
        cB.withCode("EMPTY");
        cB.withName("empty");
        cB.withMinStudents(10);
        cB.withMaxStudents(200);
        cB.withDescription("sadasdasd");
         empty = cB.build();

        cB = new CourseBuilder();
        cB.withCode("TEST1");
        cB.withName("test");
        cB.withMinStudents(10);
        cB.withMaxStudents(200);
        cB.withDescription("asdsass");
         test1 = cB.build();

        when(courseRepo.verifyCourse("EMPTY")).thenReturn(true);
        when(courseRepo.verifyCourse("TEST1")).thenReturn(true);
        when(courseRepo.verifyCourse("NULLL")).thenReturn(false);

        Exam exam1 = new Exam("examtest1","examtest1","examtest1", test1.code(), new Date(), new Date());
        Exam exam2 = new Exam("examtest2","examtest2","examtest2", test1.code(), new Date(), new Date());
        examList = List.of(exam1, exam2);

        when(examRepo.findAllOfCourse(CourseCode.valueOf("EMPTY"))).thenReturn(new ArrayList<>());
        when(examRepo.findAllOfCourse(CourseCode.valueOf("TEST1"))).thenReturn(examList);
        service = new ListCourseExamsService(examRepo, courseRepo);
    }

    @Test
    void testInvalidCourseCodeToListExamsFrom() {
        String courseCodeTest= "invalid";
        assertNull(service.allCourseExams(courseCodeTest));
    }

    @Test
    void testNonExistingCourseCodeToListExamsFrom() {
        when(courseRepo.verifyCourse("NULLL")).thenReturn(false);
        String courseCodeTest= "NULLL";
        assertNull(service.allCourseExams(courseCodeTest));
    }

    @Test
    void testValidCourseCodeWithNoExams() {
        assertTrue(service.allCourseExams(empty.code().courseCode()).isEmpty());
    }

    @Test
    void testValidCourseCodeWithExams() {
        List<Exam> result = service.allCourseExams(test1.code().courseCode());

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertArrayEquals(examList.toArray(), result.toArray());
    }
}