package ecourse.base.app.backoffice.console.presentation.UI;

import eapli.framework.presentation.console.AbstractUI;

public class ApproveApplicationUI extends AbstractUI {

    @Override
    protected boolean doShow() {
        System.out.println("Manage Student Applications\n");
        return false;
    }

    @Override
    public String headline() {
        return "Manage Applications";
    }
}
