package ecourse.base.ExamMagnament.Mapper;

import ecourse.base.ExamMagnament.FormativeExamDto;
import ecourse.base.ExamMagnament.domain.FormativeExam;

public class FormativeExamMapper {

    public static FormativeExamDto toDTO(FormativeExam exam) {
        return new FormativeExamDto(String.valueOf(exam.Code()), exam.courseCode().toString());
    }
}
