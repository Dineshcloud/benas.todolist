
package io.github.benas.todolist.web.pages.todo;

import io.github.benas.todolist.web.pages.user.Home;
import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.TodoService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Date;

/**
 * 
 */
@SuppressWarnings("unused")
public class Create {

    @SessionState
    private User loggedUser;

    @Inject
    private TodoService todoService;

    @Property
    private String title;

    @Property
    private Priority priority;

    @Property
    private Date dueDate;

    @Property
    @InjectComponent
    private Form createTodoForm;

    @OnEvent(value = EventConstants.ACTIVATE)
    public void init() {
        dueDate = new Date();
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "createTodoForm")
    public Object createTodo() {
        Todo todo = new Todo(loggedUser.getId(), title, false, priority, dueDate);
        todoService.create(todo);
        return Home.class;
    }

}
