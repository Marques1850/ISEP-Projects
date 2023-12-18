package ecourse.base.ExamMagnament.Mapper;

import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.domain.Exam;

public class ExamMapper {

    public static ExamDto toDTO(Exam exam) {
        return new ExamDto(String.valueOf(exam.Code()), exam.name(), exam.description(), exam.courseCode().toString(),
                exam.OpenDate().toString(), exam.Closedate().toString());
    }
}
