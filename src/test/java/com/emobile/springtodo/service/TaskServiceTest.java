package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.UpdateTaskDto;
import com.emobile.springtodo.model.Task;
import com.emobile.springtodo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void createTaskTest() {
        Task task = new Task("Test Task", "Description");
        Task savedTask = new Task(1L, "Test Task", "Description");

        when(taskRepository.save(task)).thenReturn(savedTask);

        Task result = taskService.createTask(task);
        assertEquals(1L, result.getId());
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    public void findByIdTest() {
        Task task = new Task(1L, "Test Task", "Description");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.findById(1L);
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    public void updateTaskTest() {
        Task existingTask = new Task(1L, "Old Title", "Old Description");
        UpdateTaskDto updateTaskDto = new UpdateTaskDto("New Title", null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Task result = taskService.updateTask(1L, updateTaskDto);

        assertEquals("New Title", result.getTitle());
        assertEquals("Old Description", result.getDescription());
    }
}

