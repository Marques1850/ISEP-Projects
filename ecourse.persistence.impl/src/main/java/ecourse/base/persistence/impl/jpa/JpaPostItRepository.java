package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.PostItMagnament.PostItRepository;
import ecourse.base.PostItMagnament.domain.PostIt;

public class JpaPostItRepository extends JpaAutoTxRepository<PostIt, Long, Long>
        implements PostItRepository {

    public JpaPostItRepository(final  String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(), "id");
    }
    public JpaPostItRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }
}
