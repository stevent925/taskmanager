package com.taskmanager;
import com.taskmanager.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integrationtest")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task(null, "Database Test", "Testing DB", null, null, null);
        taskRepository.save(sampleTask);
    }

    @Test
    void testFindById_WhenTaskExists() {
        Optional<Task> foundTask = taskRepository.findById(sampleTask.getId());

        assertTrue(foundTask.isPresent());
        assertEquals(sampleTask.getTitle(), foundTask.get().getTitle());
    }

    @Test
    void testFindById_WhenTaskDoesNotExist() {
        Optional<Task> foundTask = taskRepository.findById(999L);

        assertFalse(foundTask.isPresent());
    }
}
