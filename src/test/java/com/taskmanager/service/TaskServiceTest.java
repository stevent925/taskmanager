package com.taskmanager.service;

import com.taskmanager.TaskRepository;
import com.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task(1L, "Task 1", "First task", null, null, null);
        task2 = new Task(2L, "Task 2", "Second task", null, null, null);
    }

    @Test
    void testGetAllTasks_WhenTasksExist() {
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
        verify(taskRepository, times(1)).findAll(); // ✅ Ensure findAll() was called
    }

    @Test
    void testGetAllTasks_WhenNoTasksExist() {
        when(taskRepository.findAll()).thenReturn(List.of());

        List<Task> result = taskService.getAllTasks();

        assertTrue(result.isEmpty());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById_WhenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Optional<Task> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(task1.getId(), result.get().getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskById_WhenTaskDoesNotExist() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(999L);

        assertFalse(result.isPresent());
        verify(taskRepository, times(1)).findById(999L);
    }
}
