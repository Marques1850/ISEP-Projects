package ecourse.base.ExamMagnament.services;

import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.Mapper.ExamMapper;
import ecourse.base.ExamMagnament.Mapper.GradeMapper;
import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.ExamGrade;
import ecourse.base.ExamMagnament.domain.ExamGradeDto;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ListGradesStudentService {


    public HashMap<ExamDto, List<ExamGradeDto>> listGradesStudent(eCourseSystemUser user, List<Exam> examsIn) {
        HashMap<ExamDto, List<ExamGradeDto>> gradesMap = new HashMap<>();
        for (Exam exam: examsIn) {
            Optional<List<ExamGrade>> optGrade= exam.gradeBystudent(user);
            if( !optGrade.isEmpty() ) {
                List<ExamGrade> grades= optGrade.get();
                gradesMap.put(ExamMapper.toDTO(exam),GradeMapper.toDTO(grades));
            }
        }
        return gradesMap;
    }
}
