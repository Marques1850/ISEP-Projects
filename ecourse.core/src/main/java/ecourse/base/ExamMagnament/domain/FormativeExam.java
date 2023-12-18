package ecourse.base.ExamMagnament.domain;

import eapli.framework.domain.model.AggregateRoot;
import ecourse.base.usermanagement.domain.Course.CourseCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FormativeExam implements AggregateRoot<ExamCode> {

    @Id
    @Column(name="FORMATIVE_EXAM_CODE",unique = true)
    private ExamCode code;

    @Column(name="EXAM_COURSECODE")
    private CourseCode courseCode;

    @ElementCollection
    @Column(name = "EXAM_CONTENT")
    private List<String> content;

    @ElementCollection
    @JoinColumn(name="EXAM_GRADES")
    private List<ExamGrade> grades;


    public FormativeExam(String code, List<String> content, CourseCode courseCode) {
        this.code = ExamCode.valueOf(code);
        this.content = content;
        this.courseCode = courseCode;
        this.grades = new ArrayList<>();
    }

    public FormativeExam() {

    }


    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public ExamCode identity() {
        return null;
    }

    public List<String> getContent() {
        return content;
    }

    public List<ExamGrade> getGrades() {
        return grades;
    }

    public ExamCode Code() {
        return code;
    }

    public CourseCode courseCode() {
        return courseCode;
    }
}
