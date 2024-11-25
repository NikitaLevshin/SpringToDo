package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.UpdateTaskDto;
import com.emobile.springtodo.exception.TaskNotFoundException;
import com.emobile.springtodo.model.Task;
import com.emobile.springtodo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @CacheEvict(value = "task", allEntries = true)
    public Task createTask(Task task) {
        return taskRepository.create(task);
    }

    @Cacheable(value = "task", key = "#id")
    public Task findById(long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    @Cacheable(value = "task", key = "{#limit, #offset}")
    public List<Task> findAll(int limit, int offset) {
        return taskRepository.findAll(limit, offset);
    }

    @CacheEvict(value = "task", allEntries = true)
    public Task updateTask(long id, UpdateTaskDto updateTaskDto) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            if (!Objects.equals(updateTaskDto.getTitle(), task.getTitle()) && updateTaskDto.getTitle() != null) {
                task.setTitle(updateTaskDto.getTitle());
            }
            if (!Objects.equals(updateTaskDto.getDescription(), task.getDescription()) && updateTaskDto.getDescription() != null) {
                task.setDescription(updateTaskDto.getDescription());
            }
            return taskRepository.update(task);
        } else {
            throw new TaskNotFoundException("Task with id " + id + " not found");
        }
    }

    @CacheEvict(value = "task", key = "#id")
    public void delete(long id) {
        taskRepository.delete(id);
    }
}
