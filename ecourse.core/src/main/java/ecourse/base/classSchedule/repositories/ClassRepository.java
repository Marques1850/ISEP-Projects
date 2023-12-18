package ecourse.base.classSchedule.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.classSchedule.domain.ClassTitle;

import java.util.List;

public interface ClassRepository extends DomainRepository<ClassTitle, ClassSchedule> {

    List<ClassSchedule> getAll();

    ClassSchedule getClassByTitle(String title);

    List<ClassSchedule> getClassByDate(String date);

    List<ClassSchedule> getClassByDuration(String duration);

}
