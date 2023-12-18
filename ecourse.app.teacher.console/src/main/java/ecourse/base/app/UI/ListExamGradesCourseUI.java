package ecourse.base.app.UI;

import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.ExamMagnament.Controllers.ListExamGradesCourseController;
import ecourse.base.ExamMagnament.domain.ExamGradeDto;

import java.util.List;

public class ListExamGradesCourseUI extends AbstractUI {

    private final ListExamGradesCourseController theController = new ListExamGradesCourseController();
    @Override
    protected boolean doShow() {
        final List<ExamGradeDto> examGradesDtos = this.theController.getExamGradesOfCourse();
        if(examGradesDtos.isEmpty()) {
            System.out.println("----------No exams grades available------------");
        } else{
            for (ExamGradeDto examGradeDto : examGradesDtos) {
                System.out.println(examGradeDto.toString());

            }
        }

        return false;
    }

    @Override
    public String headline() {
        return "List Exam Grades Course";
    }
}
