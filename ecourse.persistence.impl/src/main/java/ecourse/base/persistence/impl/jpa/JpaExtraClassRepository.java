package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.classSchedule.domain.ClassTitle;
import ecourse.base.classSchedule.domain.ExtraClass;
import ecourse.base.classSchedule.repositories.ExtraClassRepository;

import java.util.ArrayList;
import java.util.List;

public class JpaExtraClassRepository extends JpaAutoTxRepository<ExtraClass, ClassTitle, ClassTitle> implements ExtraClassRepository {

    public JpaExtraClassRepository(final TransactionalContext autoTx) {
        super(autoTx, "extraClassTitle");
    }

    public JpaExtraClassRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "extraClassTitle");
    }

    @Override
    public List<ExtraClass> getAll() {
        Iterable<ExtraClass> iterable = findAll();
        List<ExtraClass> extraClasses = new ArrayList<>();
        for (ExtraClass extraClass : iterable) {
            extraClasses.add(extraClass);
        }
        return extraClasses;
    }

    @Override
    public List<ExtraClass> getExtraClassByTitle(ClassTitle title) {
        return null;
    }

    @Override
    public List<ExtraClass> getExtraClassByDate(String date) {
        return null;
    }

    @Override
    public List<ExtraClass> getExtraClassByDuration(String duration) {
        return null;
    }

    @Override
    public ExtraClass getClassByTitle(String title) {
        Iterable<ExtraClass> iterable = findAll();
        for (ExtraClass classSchedule : iterable) {
            if (classSchedule.title().classTitle().equals(title)) {
                return classSchedule;
            }
        }
        return null;
    }


}
