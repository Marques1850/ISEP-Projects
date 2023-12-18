package ecourse.base.ExamMagnament;

import ecourse.base.ExamMagnament.domain.Exam;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import java.util.Date;

public class ExamDto {
    public String code;
    public String name;
    public String description;
    public String CourseCode ;

    public String openDate;
    public String closeDate;

    public ExamDto(String code, String name, String description, String courseCode, String openDate, String closeDate) {
        this.code = code;
        this.name = name;
        this.description = description;
        CourseCode = courseCode;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    public ExamDto() {
    }

    public static ExamDto toDto(Exam exam) {
        return new ExamDto(exam.Code().toString(),
                exam.name(), exam.description(),
                exam.courseCode().toString(),
                exam.OpenDate().toString(),
                exam.Closedate().toString());
    }

    @Override
    public String toString() {
        return "Exam[ " +
                "code=" + code +
                " | name=" + name +
                " | description=" + description +
                " | CourseCode=" + CourseCode +
                " | openDate=" + openDate +
                " | closeDate=" + closeDate +
                " ] ";
    }

    public String toStringListGradesOnly(){
        return "code='"  + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", CourseCode='" + CourseCode + '\'' +
                '}';
    }
}
