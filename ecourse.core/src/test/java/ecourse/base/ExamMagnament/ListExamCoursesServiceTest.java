package ecourse.base.ExamMagnament;

import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.ExamMagnament.services.ListExamCoursesService;
import ecourse.base.usermanagement.domain.Course.Course;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListExamCoursesServiceTest {
   private  ExamRepository repo ;
   private final ListExamCoursesService listExamCoursesService = new ListExamCoursesService();
    @BeforeEach
    void setUp() {
        repo = mock(ExamRepository.class);

    }

    @Test
    void getFutureExamsTestAllValid() throws ParseException {
        List<Course> courses = new ArrayList<>();
        Course course = new Course("1",CourseCode.valueOf("MATHS1"));
        courses.add(course);
        List<Exam> exams = new ArrayList<>();
        List<Exam> expectedExams= new ArrayList<>();
        Exam exam = new Exam("1","Math1","Math First Test", CourseCode.valueOf("MATHS1"), DateUtils.addDays(new Date(),1),DateUtils.addDays(new Date(),1));
        exams.add(exam);
        expectedExams.add(exam);
        Exam exam1 = new Exam("1","Math2","Math Second Test", CourseCode.valueOf("MATHS2"),DateUtils.addDays(new Date(),2),DateUtils.addDays(new Date(),2));
        exams.add(exam1);
        expectedExams.add(exam1);


        when(repo.findAllOfCourse(CourseCode.valueOf("MATHS1"))).thenReturn(exams);
        List<Exam>resultExams=listExamCoursesService.getFutureExams(courses,repo);
        assertEquals(expectedExams,resultExams);
    }
@Test
    void getFutureExamsTestValidInvalidTest() throws ParseException {
        List<Course> courses = new ArrayList<>();
        Course course = new Course("1",CourseCode.valueOf("MATHS1"));
        courses.add(course);
        List<Exam> exams = new ArrayList<>();
      List<Exam> expectedExams= new ArrayList<>();
       Exam exam = new Exam("1","Math1","Math First Semester", CourseCode.valueOf("MATHS1"),DateUtils.addDays(new Date(),-1),DateUtils.addDays(new Date(),-1));
        exams.add(exam);
        Exam exam1 = new Exam("1","Math1","Math First Semester", CourseCode.valueOf("MATHS1"),DateUtils.addDays(new Date(),1),DateUtils.addDays(new Date(),1));
        exams.add(exam1);
        expectedExams.add(exam1);
        when(repo.findAllOfCourse(CourseCode.valueOf("MATHS1"))).thenReturn(exams);
        List<Exam>resultExams=listExamCoursesService.getFutureExams(courses,repo);
        assertEquals(expectedExams,resultExams);


    }
}