package ecourse.base.ExamMagnament.Controllers;


import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.util.Date;

  //This is a improvised controller for quickly adding incomplete exams to testing, it will be changed in the future
public class CreateNewExamController {
    private final ExamRepository ExamRepository = PersistenceContext.repositories().exams();


       public void createExam(String code, String name, String description, CourseCode courseCode, Date openDate, Date closeDate){
              Exam exam = new Exam(code,name,description, courseCode,openDate,closeDate);
              ExamRepository.save(exam);
       }


    public boolean addExam(Exam exam) {
        ExamRepository.save(exam);
        return true;
    }
}


