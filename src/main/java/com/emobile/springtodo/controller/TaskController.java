package com.emobile.springtodo.controller;

import com.emobile.springtodo.controller.swagger.ITaskController;
import com.emobile.springtodo.dto.NewTaskDto;
import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.dto.UpdateTaskDto;
import com.emobile.springtodo.mapper.TaskMapper;
import com.emobile.springtodo.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController implements ITaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(NewTaskDto newTaskDto) {
        return TaskMapper.toTaskDto(taskService.createTask(TaskMapper.fromNewTaskDto(newTaskDto)));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTask(@PathVariable int id) {
        return TaskMapper.toTaskDto(taskService.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getTasks(@RequestParam (required = false, defaultValue = "25") int size,
                                  @RequestParam(required = false, defaultValue = "0") int page) {
        return TaskMapper.toTaskDtoList(taskService.findAll(size, page));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTask(@PathVariable int id, UpdateTaskDto updateTaskDto) {
        return TaskMapper.toTaskDto(taskService.updateTask(id, updateTaskDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable int id) {
        taskService.delete(id);
    }
}
