package ecourse.base.app.user.console.presentation;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.ExamMagnament.Controllers.TakeExamController;
import ecourse.base.ExamMagnament.Controllers.TakeFormativeExamController;
import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.FormativeExamDto;

import java.util.List;

public class TakeFormativeExamUI extends AbstractUI {

    private final TakeFormativeExamController theController = new TakeFormativeExamController();
    @Override
    protected boolean doShow() {
        final List<FormativeExamDto> exams = this.theController.getTakeableFormativeExams();
        if(exams.isEmpty()) {
            System.out.println("----------No formative exams available to take------------");
        } else{
            showFormativeExams(exams);

            String examCode = exams.get(exams.size()-1).code;

            theController.takeFormativeExam(examCode.substring(18,examCode.length()-1));
        }

        return false;
    }

    private boolean showFormativeExams(final List<FormativeExamDto> exams) {

        final Menu ExamsMenu = buildExamsMenu(exams);
        final MenuRenderer renderer = new VerticalMenuRenderer(ExamsMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildExamsMenu(final List<FormativeExamDto> exams) {
        final Menu ExamsMenu = new Menu();
        int counter = 0;
        for (final FormativeExamDto exam : exams) {
            ExamsMenu.addItem(MenuItem.of(counter++, exam.toString(), () -> exams.add(exam)));
        }
        return ExamsMenu;
    }

    @Override
    public String headline() {
        return "Take Formative Exam";
    }
}
