package ecourse.base.classSchedule.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import ecourse.base.classSchedule.domain.ClassTitle;
import ecourse.base.classSchedule.domain.ExtraClass;

import java.util.List;

public interface ExtraClassRepository extends DomainRepository<ClassTitle, ExtraClass> {

    List<ExtraClass> getAll();

    List<ExtraClass> getExtraClassByTitle(ClassTitle title);

    List<ExtraClass> getExtraClassByDate(String date);

    List<ExtraClass> getExtraClassByDuration(String duration);

    ExtraClass getClassByTitle(String title);

}
