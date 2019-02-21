

package io.github.benas.todolist.web.pages.user;

import io.github.benas.todolist.web.common.util.TodoListUtils;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.TodoService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.text.SimpleDateFormat;
import java.util.List;

import static io.github.benas.todolist.web.common.util.TodoListUtils.DATE_FORMAT;

/**
 * 
 */
@SuppressWarnings("unused")
public class Home {

    @Inject
    private TodoService todoService;

    @SessionState
    private User loggedUser;

    @Property
    private List<Todo> todoList;

    @Property
    private Todo currentTodo;

    @Property
    private int doneCount;

    @Property
    private int todoCount;

    @Property
    private int totalCount;

    @OnEvent(value = EventConstants.ACTIVATE)
    public void init() {
        Long loggedUserId = loggedUser.getId();
        todoList = todoService.getTodoListByUser(loggedUserId);
        totalCount = todoList.size();
        doneCount = TodoListUtils.countTotalDone(todoList);
        todoCount = totalCount - doneCount;
    }

    @OnEvent(value = EventConstants.ACTION, component = "deleteTodoLink")
    public void deleteTodo(long todoId) {
        Todo todo = todoService.getTodoById(todoId);
        if (todo != null) {
            todoService.remove(todo);
        }
    }

    public String getCurrentStatusLabel() {
        return TodoListUtils.getStatusLabel(currentTodo.isDone());
    }

    public String getCurrentStatusStyle() {
        return TodoListUtils.getStatusStyle(currentTodo.isDone());
    }

    public String getCurrentPriorityIcon() {
        return TodoListUtils.getPriorityIcon(currentTodo.getPriority());
    }

    public String getCurrentDueDate() {
        return new SimpleDateFormat(DATE_FORMAT).format(currentTodo.getDueDate());
    }

}
