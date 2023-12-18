package ecourse.base.classSchedule.application;

import ecourse.base.classSchedule.domain.ClassSchedule;
import ecourse.base.classSchedule.repositories.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduleClassServiceTest {

    private ClassRepository repo;

    @BeforeEach
    void setUp() {
        repo = mock(ClassRepository.class);

    }
    @Test
    void createClassScheduleNotNull() {

        ClassSchedule classSchedule1 = new ClassSchedule("Teste1", 90, LocalDateTime.now());
        assertNotNull(classSchedule1);

    }

    @Test
    void createClassScheduleTest(){

        ClassSchedule classSchedule1 = new ClassSchedule("ClassTest1", 90, LocalDateTime.now());
        repo.save(classSchedule1);

        ClassSchedule classSchedule2 = new ClassSchedule("ClassTest2", 90, LocalDateTime.now().plusHours(5));
        repo.save(classSchedule2);

        ClassSchedule classSchedule3 = new ClassSchedule("ClassTest3", 90, LocalDateTime.now().plusHours(10));
        repo.save(classSchedule3);

        when(repo.getClassByTitle("ClassTest1")).thenReturn(classSchedule1);
        when(repo.getClassByTitle("ClassTest2")).thenReturn(classSchedule2);
        when(repo.getClassByTitle("ClassTest3")).thenReturn(classSchedule3);

        assertEquals(classSchedule1, repo.getClassByTitle("ClassTest1"));
        assertEquals(classSchedule2, repo.getClassByTitle("ClassTest2"));
        assertEquals(classSchedule3, repo.getClassByTitle("ClassTest3"));

    }
}