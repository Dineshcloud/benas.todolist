

package io.github.todolist.core.repository.impl;

import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.repository.api.TodoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link TodoRepository} using JPA.
 *
 * 
 */
@Repository
public class TodoRepositoryImpl implements TodoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    public Todo getTodoById(final long id) {
        return entityManager.find(Todo.class, id);
    }

    /**
     * {@inheritDoc}
     */
    public List<Todo> getTodoListByUser(final long userId) {
        TypedQuery<Todo> query = entityManager.createNamedQuery("findTodosByUser", Todo.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public List<Todo> getTodoListByUserAndTitle(final long userId, final String title) {
        TypedQuery<Todo> query = entityManager.createNamedQuery("findTodosByTitle", Todo.class);
        query.setParameter(1, userId);
        query.setParameter(2, "%" + title.toUpperCase() + "%");
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public Todo update(Todo todo) {
        return entityManager.merge(todo);
    }

    /**
     * {@inheritDoc}
     */
    public Todo create(final Todo todo) {
        entityManager.persist(todo);
        return todo;
    }

    /**
     * {@inheritDoc}
     */
    public void remove(final Todo todo) {
        Todo t = entityManager.find(Todo.class, todo.getId());
        entityManager.remove(t);
    }

}
