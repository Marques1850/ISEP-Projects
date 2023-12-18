package ecourse.base.app.user.console.presentation;
import eapli.framework.presentation.console.AbstractUI;
import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.Controllers.ListGradesStudentController;
import ecourse.base.ExamMagnament.domain.ExamGradeDto;

import java.util.HashMap;
import java.util.List;

public class ListGradesStudentsUI extends AbstractUI {
    private final ListGradesStudentController theController = new ListGradesStudentController();

    @Override
    protected boolean doShow(){
        final HashMap<ExamDto, List<ExamGradeDto>> Grades = theController.listGradesStudent();
        if(Grades.isEmpty()){
            System.out.println("----------No grades available------------");
        }else{
            int counter = 0;
            int counterExam=0;
            for (ExamDto examDto: Grades.keySet()) {
                System.out.println("Exam: "+ counterExam+"\n");
                System.out.println(examDto.toString()+"\n");
                for (ExamGradeDto examGradeDto: Grades.get(examDto)) {
                    System.out.println("Grade: "+counter+"\n");
                    System.out.println(examGradeDto.toString()+"\n");
                    counter++;
                }
                System.out.println("--------------------------------------------------------------------------------------------------------\n");
                counter=0;
            }
        }

        return true;
    }

    @Override
    public String headline() {
        return "Request List Grades Students";}


}
