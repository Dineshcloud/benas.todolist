
package io.github.benas.todolist.web.action.todo;

import com.opensymphony.xwork2.Action;
import io.github.benas.todolist.web.action.BaseAction;
import io.github.todolist.core.domain.Todo;

import java.text.MessageFormat;

/**
 * Action class for Todo CRUD operations.
 *
 * 
 */
public class TodoAction extends BaseAction {

    private String error;

    private Todo todo;

    private long todoId;

    public String create() {
        return Action.SUCCESS;
    }

    public String doCreate() {
        todo.setUserId(getSessionUser().getId());
        todoService.create(todo);
        return Action.SUCCESS;
    }

    public String update() {
        todo = todoService.getTodoById(todoId);
        //FIXME the todo should belong to the logged user
        return Action.SUCCESS;
    }

    public String doUpdate() {

        Todo t = todoService.getTodoById(todoId); // Unable to update the model "todo" since there is no setter for the id
        t.setDone(todo.isDone());
        t.setDueDate(todo.getDueDate());
        t.setPriority(todo.getPriority());
        t.setTitle(todo.getTitle());
        t.setUserId(todo.getUserId());
        todoService.update(t);
        return Action.SUCCESS;
    }

    public String doDelete() {
        Todo todo = todoService.getTodoById(todoId);
        //FIXME the todo should belong to the logged user
        if (todo != null) {
            todoService.remove(todo);
            return Action.SUCCESS;
        } else {
            error = MessageFormat.format(getText("no.such.todo"), todoId);
            return Action.ERROR;
        }
    }

    /*
    * Getters for model attributes
    */
    public Todo getTodo() {
        return todo;
    }

    public String getError() {
        return error;
    }

    /*
     * Setters for request parameters binding
     */
    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }

}
