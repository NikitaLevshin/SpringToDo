package com.emobile.springtodo.repository;

import com.emobile.springtodo.exception.TaskNotFoundException;
import com.emobile.springtodo.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class TaskRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Task> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery("from Task where id = :id", Task.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResultOrNull());
        }
    }

    public List<Task> findAll(int limit, int offset) {
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery("from Task", Task.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        }
    }

    public Task create(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(task);
            session.getTransaction().commit();
            return task;
        }
    }

    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Task task = findById(id).orElseThrow(
                    () -> new TaskNotFoundException("Task with id " + id + " not found")
            );
            session.remove(task);
            session.getTransaction().commit();
        }
    }

    public Task update(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(task);
            session.getTransaction().commit();
            return task;
        }
    }
}
