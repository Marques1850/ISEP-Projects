package ecourse.base.app.user.console.presentation;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.ExamMagnament.Controllers.TakeExamController;
import ecourse.base.ExamMagnament.ExamDto;

import java.util.List;

public class TakeExamUI extends AbstractUI {

    private final TakeExamController theController = new TakeExamController();
    @Override
    protected boolean doShow() {
        final List<ExamDto> exams = this.theController.getTakeableExams();
        if(exams.isEmpty()) {
            System.out.println("----------No exams available to take------------");
        } else{
            showExams(exams);

            String examCode = exams.get(exams.size()-1).code;

            theController.takeExam(examCode.substring(18,examCode.length()-1));
        }

        return false;
    }

    private boolean showExams(final List<ExamDto> exams) {

        final Menu ExamsMenu = buildExamsMenu(exams);
        final MenuRenderer renderer = new VerticalMenuRenderer(ExamsMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildExamsMenu(final List<ExamDto> exams) {
        final Menu ExamsMenu = new Menu();
        int counter = 0;
        for (final ExamDto exam : exams) {
            ExamsMenu.addItem(MenuItem.of(counter++, exam.toString(), () -> exams.add(exam)));
        }
        return ExamsMenu;
    }

    @Override
    public String headline() {
        return "Take Exam";
    }
}
