package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.UpdateTaskDto;
import com.emobile.springtodo.model.Task;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);

    Task findById(long id);

    List<Task> findAll(int limit, int offset);

    Task updateTask(long id, UpdateTaskDto updateTaskDto);

    void delete(long id);


}
