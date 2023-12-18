package ecourse.base.PostItMagnament;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import ecourse.base.infrastructure.persistence.PersistenceContext;

public class UndoLastChangePostItController {
    static UndoLastChangePostItController instance;
    private static UndoLastChangePostItService service;
    public static UndoLastChangePostItController getInstance() {
        if(instance == null){
            instance = new UndoLastChangePostItController();
        }
        return instance;
    }

    public UndoLastChangePostItController() {
        this.service = new UndoLastChangePostItService(
                PersistenceContext.repositories().BoardRepository(),
                PersistenceContext.repositories().BoardChangesRepository()
        );
    }

    public PostItDto undoLastChangePostIt(String boardId,int row,int column){
        EmailAddress sessionEmail = AuthzRegistry.authorizationService().session().get().authenticatedUser().email();
        return service.undoLastChangePostIt(boardId,row,column,sessionEmail);
    }
}
