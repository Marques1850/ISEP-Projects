package ecourse.base.app.user.console.presentation;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import ecourse.base.ExamMagnament.ExamDto;
import ecourse.base.ExamMagnament.ListFutureExam.ListFutureExamsController;

import java.util.List;

public class ListFutureExamsUI extends AbstractUI {
    private final ListFutureExamsController theController = new ListFutureExamsController();

    @Override
    protected boolean doShow() {
       final List<ExamDto> futureExams = this.theController.getFutureExams();
       if(futureExams.isEmpty()) {
           System.out.println("----------No future exams available------------");
       } else{
           for (ExamDto examDto : futureExams) {
               System.out.println(examDto.toString());

           }
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
        return "List Future Exams";
    }

}
