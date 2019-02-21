

package io.github.todolist.core.service.impl;

import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.repository.api.TodoRepository;
import io.github.todolist.core.service.api.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link TodoService}.
 *
 * 
 */
@Service
@Transactional(readOnly = true)
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    /**
     * {@inheritDoc}
     */
    public Todo getTodoById(final long id) {
        return todoRepository.getTodoById(id);
    }

    /**
     * {@inheritDoc}
     */
    public List<Todo> getTodoListByUser(final long userId) {
        return todoRepository.getTodoListByUser(userId);
    }

    /**
     * {@inheritDoc}
     */
    public List<Todo> searchTodoListByTitle(final long userId, final String title) {
        return todoRepository.getTodoListByUserAndTitle(userId, title);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Todo update(Todo todo) {
        return todoRepository.update(todo);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Todo create(final Todo todo) {
        return todoRepository.create(todo);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void remove(final Todo todo) {
        todoRepository.remove(todo);
    }
}
