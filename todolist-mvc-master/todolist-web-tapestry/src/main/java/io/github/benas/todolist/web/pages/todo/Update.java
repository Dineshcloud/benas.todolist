

package io.github.benas.todolist.web.pages.todo;

import io.github.benas.todolist.web.pages.user.Home;
import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.TodoService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Date;

/**
 * 
 */
@SuppressWarnings("unused")
public class Update {

    @SessionState
    private User loggedUser;

    @Inject
    private TodoService todoService;

    @Inject
    private Messages messages;

    @Property
    private String title;

    @Property
    private Priority priority;

    @Property
    private boolean status;

    @Property
    private Date dueDate;

    @Persist
    @Property
    private Todo todo;

    @Property
    private String error;

    @InjectPage
    private io.github.benas.todolist.web.pages.Error errorPage;

    @Property
    @InjectComponent
    private Form updateTodoForm;

    @OnEvent(value = EventConstants.ACTIVATE)
    public Object init(long todoId) {
        todo = todoService.getTodoById(todoId);
        if (todo == null) {
            errorPage.setErrorMessage(messages.format("no.such.todo", todoId));
            return errorPage;
        }
        return null;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "updateTodoForm")
    public Object updateTodo() {
        todo.setTitle(title);
        todo.setDueDate(dueDate);
        todo.setPriority(priority);
        todo.setDone(status);
        todoService.update(todo);
        return Home.class;
    }

}
