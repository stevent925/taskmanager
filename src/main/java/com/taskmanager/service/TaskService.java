package com.taskmanager.service;

import com.taskmanager.TaskRepository;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }


    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public boolean updateStatus(Long id, TaskStatus status) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setStatus(status);
            taskRepository.save(task);
            return true;
        }
        return false;
    }

    public boolean deleteTask(Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
