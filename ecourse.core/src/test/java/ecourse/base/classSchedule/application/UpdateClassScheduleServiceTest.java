package ecourse.base.classSchedule.application;

import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.classSchedule.domain.ExtraClass;
import ecourse.base.classSchedule.repositories.ClassRepository;
import ecourse.base.classSchedule.repositories.ExtraClassRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateClassScheduleServiceTest {

    private ClassRepository repo;

    @BeforeEach
    void setUp() {
        repo = mock(ClassRepository.class);
    }

    @Test
    void updateClassSchedule() {

        ClassSchedule classSchedule1 = new ClassSchedule("ClassTest1", 90, LocalDateTime.now());
        repo.save(classSchedule1);

        when(repo.getAll()).thenReturn(List.of(classSchedule1));
        assertEquals(classSchedule1, repo.getAll().get(0));



    }
}
