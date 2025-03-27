package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        task1 = new Task(1L, "Task 1", "First task", null, null, null);
        task2 = new Task(2L, "Task 2", "Second task", null, null, null);
        task3 = new Task(null, "New Task", "This is a test task", null, null, null);
    }


    @Test
    void testCreateTask() {
        when(taskService.createTask(any(Task.class))).thenReturn(new Task(1L, "New Task", "This is a test task", null, null, null));

        ResponseEntity<Task> response = taskController.createTask(task3);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void testGetAllTasks_WhenTasksExist() {
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskService.getAllTasks()).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testGetAllTasks_WhenNoTasksExist() {
        when(taskService.getAllTasks()).thenReturn(List.of());

        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(taskService, times(1)).getAllTasks();
    }


    @Test
    void testGetTaskById_WhenTaskExists() {
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task1));

        ResponseEntity<?> response = taskController.getTaskById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(task1, response.getBody());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void testGetTaskById_WhenTaskDoesNotExist() {
        when(taskService.getTaskById(999L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = taskController.getTaskById(999L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Task with id: 999 not found", response.getBody());
        verify(taskService, times(1)).getTaskById(999L);
    }
}
