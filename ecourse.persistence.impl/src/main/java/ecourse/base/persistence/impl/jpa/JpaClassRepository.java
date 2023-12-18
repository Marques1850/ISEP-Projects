package ecourse.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.Application;
import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.classSchedule.domain.ClassTitle;
import ecourse.base.classSchedule.repositories.ClassRepository;

import java.util.ArrayList;
import java.util.List;

public class JpaClassRepository extends JpaAutoTxRepository<ClassSchedule, ClassTitle, ClassTitle>  implements ClassRepository {



    public JpaClassRepository(final TransactionalContext autoTx) {
        super(autoTx, "classTitle");
    }

    public JpaClassRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "classTitle");
    }

    @Override
    public List<ClassSchedule> getAll() {
        Iterable<ClassSchedule> iterable = findAll();
        List<ClassSchedule> classes = new ArrayList<>();
        for (ClassSchedule classSchedule : iterable) {
            classes.add(classSchedule);
        }
        return classes;
    }

    @Override
    public ClassSchedule getClassByTitle(String title) {
        Iterable<ClassSchedule> iterable = findAll();
        for (ClassSchedule classSchedule : iterable) {
            if (classSchedule.title().classTitle().equals(title)) {
                return classSchedule;
            }
        }
        return null;
    }

    @Override
    public List<ClassSchedule> getClassByDate(String date) {
        return null;
    }

    @Override
    public List<ClassSchedule> getClassByDuration(String duration) {
        return null;
    }
}
