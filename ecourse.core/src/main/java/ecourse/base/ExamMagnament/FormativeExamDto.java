package ecourse.base.ExamMagnament;

import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.ExamMagnament.domain.FormativeExam;

public class FormativeExamDto {
    public String code;
    public String CourseCode ;


    public FormativeExamDto(String code, String courseCode) {
        this.code = code;
        CourseCode = courseCode;
    }

    public FormativeExamDto() {
    }

    public static FormativeExamDto toDto(FormativeExam exam) {
        return new FormativeExamDto(exam.Code().toString(),
                exam.courseCode().toString());
    }

    @Override
    public String toString() {
        return "FormativeExam[ " +
                "code=" + code +
                " | CourseCode=" + CourseCode +
                " ] ";
    }
}
