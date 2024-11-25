package com.emobile.springtodo.mapper;


import com.emobile.springtodo.dto.NewTaskDto;
import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.dto.UpdateTaskDto;
import com.emobile.springtodo.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskMapper {

        public static Task fromNewTaskDto(NewTaskDto newTaskDto) {
            Task task = new Task();
            task.setTitle(newTaskDto.getTitle());
            task.setDescription(newTaskDto.getDescription());
            return task;
        }

    public static Task fromUpdatedTaskDto(UpdateTaskDto updateTaskDto) {
        Task task = new Task();
        task.setTitle(updateTaskDto.getTitle());
        task.setDescription(updateTaskDto.getDescription());
        return task;
    }


        public static TaskDto toTaskDto(Task task) {
            TaskDto taskDto = new TaskDto();
            taskDto.setId(task.getId());
            taskDto.setTitle(task.getTitle());
            taskDto.setDescription(task.getDescription());
            return taskDto;
        }

        public static List<TaskDto> toTaskDtoList(List<Task> tasks) {
            return tasks.stream()
                    .map(TaskMapper::toTaskDto)
                    .collect(Collectors.toList());
        }

        public static Map<String, Object> toMap(Task task) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", task.getTitle());
            map.put("description", task.getDescription());
            return map;
        }

        public static Task mapRowToTask(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setId(rs.getInt("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            return task;
        }
}
