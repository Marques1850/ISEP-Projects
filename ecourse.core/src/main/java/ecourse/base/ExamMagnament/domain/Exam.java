package ecourse.base.ExamMagnament.domain;

import eapli.framework.domain.model.AggregateRoot;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.usermanagement.domain.Course.CourseCode;
import ecourse.base.usermanagement.domain.FundamentalClasses.Grade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Embeddable
public class Exam implements AggregateRoot<ExamCode> {

    @Id
    @Column(name="EXAM_CODE",unique = true)
    private ExamCode code;
    @Column(name="EXAM_NAME")
    private String name;
    @Column(name="EXAM_DESCRIPTION")
    private String description;
    @Column(name="EXAM_COURSECODE")
    private CourseCode courseCode;
    @Column(name="EXAM_OPENDATE")
    private Date openDate;
    @Column(name="EXAM_CLOSEDATE")
    private ExamDate closeDate;

    @ElementCollection
    @JoinColumn(name="EXAM_GRADES")
    private List<ExamGrade> grades;
    @ElementCollection
    @Column(name = "EXAM_CONTENT")
    private List<String> content;

    public Exam(String code, String name, String description, CourseCode courseCode, Date openDate, Date closeDate) {
        this.code = ExamCode.valueOf(code);
        this.name = name;
        this.description = description;
        this.courseCode = courseCode;
        this.openDate =openDate;
        this.closeDate = new ExamDate(closeDate);
        this.content = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public Exam(String code, String title, CourseCode courseCode, Date openDate, Date closeDate, List<String> content) {
        this.code = ExamCode.valueOf(code);
        this.name = title;
        this.courseCode = courseCode;
        this.openDate =openDate;
        this.closeDate = new ExamDate(closeDate);
        this.content = content;
        this.grades = new ArrayList<>();
    }

    public Exam() {

    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }



    public ExamCode Code() {
        return code;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public CourseCode courseCode() {
        return courseCode;
    }

    public Optional<List<ExamGrade>> gradeBystudent(eCourseSystemUser student) {
        List<ExamGrade> examGrades= new ArrayList<>();
        for (ExamGrade grade : grades) {
            if (grade.student().eCourseUserEmail().equals(student.eCourseUserEmail())) {
                examGrades.add(grade);
            }
        }
        if(examGrades.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(examGrades);
    }




    public ExamDate Closedate() {
        return closeDate;
    }

    public Date OpenDate() {
        return openDate;
    }



    @Override
    public ExamCode identity() {
        return code;
    }

    public List<ExamGrade> getGrades() {
        return grades;
    }

    public void addExamGrade(ExamGrade examGrade) {
        grades.add(examGrade);
    }

    public List<String> getContent() {
        return content;
    }
}
