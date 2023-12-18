package ecourse.base.ExamMagnament.domain;

public class ExamGradeDto {
    private String student;
    private String grade;
    private String exam;

    public String getExam() {
        return exam;
    }
    public String getStudent() {
        return student;
    }
    public String getGrade() {
        return grade;
    }
    public void setStudent(String student) {
        this.student = student;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public void setExam(String exam) {
        this.exam = exam;
    }

    @Override
    public String toString() {
        return "ExamGradeDto{" +
                "student='" + student + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
