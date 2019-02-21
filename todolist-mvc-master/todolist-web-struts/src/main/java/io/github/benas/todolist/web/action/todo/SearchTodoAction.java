

package io.github.benas.todolist.web.action.todo;

import com.opensymphony.xwork2.Action;
import io.github.benas.todolist.web.action.BaseAction;
import io.github.todolist.core.domain.Todo;

import java.util.List;

/**
 * Action class to search todo list by title.
 *
 * 
 */
public class SearchTodoAction extends BaseAction {

    private String title;

    List<Todo> todoList;

    public String execute() {
        todoList = todoService.searchTodoListByTitle(getSessionUser().getId(), title);
        return Action.SUCCESS;
    }

    /*
     * Getters for model attributes
     */
    public String getTitle() {
        return title;
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    /*
     * Setters for request parameters binding
     */

    public void setTitle(String title) {
        this.title = title;
    }

}
