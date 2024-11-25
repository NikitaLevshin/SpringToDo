package com.emobile.springtodo.controller;

import com.emobile.springtodo.dto.NewTaskDto;
import com.emobile.springtodo.dto.UpdateTaskDto;
import com.emobile.springtodo.mapper.TaskMapper;
import com.emobile.springtodo.model.Task;
import com.emobile.springtodo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void createTaskTest() throws Exception {
        NewTaskDto newTaskDto = new NewTaskDto("Test Task", "Test description");

        when(taskService.createTask(any(Task.class))).thenReturn(TaskMapper.fromNewTaskDto(newTaskDto));

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newTaskDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    public void getTaskByIdTest() throws Exception {
        Task createdTask = new Task(1L, "Test Task", "Test description");

        when(taskService.findById(1L)).thenReturn(createdTask);

        mockMvc.perform(get("/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test description"));
    }

    @Test
    public void updateTaskTest() throws Exception {
        Task createdTask = new Task(1L, "Test Task", "Test description");

        UpdateTaskDto updateTaskDto = new UpdateTaskDto("Updated Task", "Updated description");
        Task updatedTask = new Task(1L, "Updated Task", "Updated description");

        when(taskService.createTask(any(Task.class))).thenReturn(createdTask);
        when(taskService.updateTask(eq(1L), any(UpdateTaskDto.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/task/" + createdTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateTaskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));


    }

    @Test
    public void deleteTaskByIdTest() throws Exception {
        NewTaskDto newTaskDto = new NewTaskDto("Test Task", "Test description");

        when(taskService.createTask(any(Task.class))).thenReturn(TaskMapper.fromNewTaskDto(newTaskDto));

        Task created = taskService.createTask(TaskMapper.fromNewTaskDto(newTaskDto));



        mockMvc.perform(delete("/task/" + created.getId()))
                .andExpect(status().isNoContent());
    }

}
