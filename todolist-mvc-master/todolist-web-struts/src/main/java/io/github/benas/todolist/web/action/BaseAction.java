

package io.github.benas.todolist.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import io.github.benas.todolist.web.common.util.TodoListUtils;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.TodoService;
import io.github.todolist.core.service.api.UserService;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;

/**
 * Base action declaring common services and objects.
 *
 * 
 */
public class BaseAction extends ActionSupport implements Preparable {

    protected TodoService todoService;

    protected UserService userService;

    protected Map<String, Object> session = ActionContext.getContext().getSession();

    protected Validator validator;

    @Override
    public void prepare() throws Exception {
        //initialize JSR 303 validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected User getSessionUser() {
        return (User) session.get(TodoListUtils.SESSION_USER);
    }

    /*
     * Setters for dependencies injection
     */

    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
