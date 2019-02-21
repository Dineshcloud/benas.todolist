

package io.github.benas.todolist.web.action.user;

import com.opensymphony.xwork2.Action;
import io.github.benas.todolist.web.action.BaseAction;
import io.github.benas.todolist.web.common.util.TodoListUtils;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.domain.User;

import java.util.List;

/**
 * Action class to load user's todo list in home page.
 * <p/>
 * 
 */
public class HomeAction extends BaseAction {

    private List<Todo> todoList;

    private int totalCount;

    private int doneCount;

    private int todoCount;

    public String execute() {
        User user = getSessionUser();
        todoList = todoService.getTodoListByUser(user.getId());
        totalCount = todoList.size();
        doneCount = TodoListUtils.countTotalDone(todoList);
        todoCount = totalCount - doneCount;
        return Action.SUCCESS;
    }

    /*
     * Getters for model attributes
     */

    public List<Todo> getTodoList() {
        return todoList;
    }

    public String getHomeTabStyle() {
        return "active";
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public int getTodoCount() {
        return todoCount;
    }

}
