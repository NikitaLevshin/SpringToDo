package com.emobile.springtodo.repository;

import com.emobile.springtodo.mapper.TaskMapper;
import com.emobile.springtodo.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Task> findById(Long id) {
        String sql = "SELECT * FROM task WHERE id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, TaskMapper::mapRowToTask, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Task> findAll(int limit, int offset) {
        String sql = "SELECT * FROM task LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, TaskMapper::mapRowToTask, limit, offset);
    }

    public Task create(Task task) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("task")
                .usingGeneratedKeyColumns("id");
        long id = insert.executeAndReturnKey(TaskMapper.toMap(task)).longValue();
        task.setId(id);
        return task;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM task WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Task update(Task task) {
        String sql = "UPDATE task SET title = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getId());
        return task;
    }
}
