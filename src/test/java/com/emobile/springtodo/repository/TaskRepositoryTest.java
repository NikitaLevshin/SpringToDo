package com.emobile.springtodo.repository;

import com.emobile.springtodo.model.Task;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void createTaskTest() {
        Task task = new Task(0, "Test Task", "Description");
        Task createdTask = taskRepository.save(task);

        assertEquals("Test Task", createdTask.getTitle());
        assertEquals("Description", createdTask.getDescription());
    }

    @Test
    public void findByIdTest() {
        Task task = new Task(0, "Test Task", "Description");
        Task createdTask = taskRepository.save(task);


        Optional<Task> foundTask = taskRepository.findById(createdTask.getId());
        assertTrue(foundTask.isPresent());
        assertEquals(createdTask.getId(), foundTask.get().getId());
        assertEquals("Test Task", foundTask.get().getTitle());
    }

    @Test
    @Order(1)
    public void findAllTasksTest() {
        taskRepository.save(new Task(0, "Task 1", "Description 1"));
        taskRepository.save(new Task(0, "Task 2", "Description 2"));

        Pageable pageable = PageRequest.of(0, 10);
        List<Task> tasks = taskRepository.findAllTasks(pageable);
        for (Task task : tasks) {
            System.out.println(task.getTitle());
        }
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    @Test
    public void updateTaskTest() {
        Task task = new Task(0, "Old Title", "Old Description");
        Task createdTask = taskRepository.save(task);

        createdTask.setTitle("New Title");
        createdTask.setDescription("New Description");
        Task updatedTask = taskRepository.save(createdTask);

        assertEquals("New Title", updatedTask.getTitle());
        assertEquals("New Description", updatedTask.getDescription());
    }

    @Test
    public void deleteTaskTest() {
        Task task = new Task(0, "Task to Delete", "Description");
        Task createdTask = taskRepository.save(task);

        taskRepository.deleteById(createdTask.getId());

        Optional<Task> foundTask = taskRepository.findById(createdTask.getId());
        assertFalse(foundTask.isPresent());
    }
}

