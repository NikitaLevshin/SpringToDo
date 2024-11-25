package com.emobile.springtodo.repository;

import com.emobile.springtodo.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void createTaskTest() {
        Task task = new Task(0, "Test Task", "Description");
        Task createdTask = taskRepository.create(task);

        assertEquals("Test Task", createdTask.getTitle());
        assertEquals("Description", createdTask.getDescription());
    }

    @Test
    public void findByIdTest() {
        Task task = new Task(0, "Test Task", "Description");
        Task createdTask = taskRepository.create(task);

        Optional<Task> foundTask = taskRepository.findById(createdTask.getId());
        assertTrue(foundTask.isPresent());
        assertEquals(createdTask.getId(), foundTask.get().getId());
        assertEquals("Test Task", foundTask.get().getTitle());
    }

    @Test
    public void findAllTasksTest() {
        taskRepository.create(new Task(0, "Task 1", "Description 1"));
        taskRepository.create(new Task(0, "Task 2", "Description 2"));

        List<Task> tasks = taskRepository.findAll(10, 0);
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    @Test
    public void updateTaskTest() {
        Task task = new Task(0, "Old Title", "Old Description");
        Task createdTask = taskRepository.create(task);

        createdTask.setTitle("New Title");
        createdTask.setDescription("New Description");
        Task updatedTask = taskRepository.update(createdTask);

        assertEquals("New Title", updatedTask.getTitle());
        assertEquals("New Description", updatedTask.getDescription());
    }

    @Test
    public void deleteTaskTest() {
        Task task = new Task(0, "Task to Delete", "Description");
        Task createdTask = taskRepository.create(task);

        taskRepository.delete(createdTask.getId());

        Optional<Task> foundTask = taskRepository.findById(createdTask.getId());
        assertFalse(foundTask.isPresent());
    }
}

