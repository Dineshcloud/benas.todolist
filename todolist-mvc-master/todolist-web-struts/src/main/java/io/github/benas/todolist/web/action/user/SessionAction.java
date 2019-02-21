

package io.github.benas.todolist.web.action.user;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import io.github.benas.todolist.web.action.BaseAction;
import io.github.benas.todolist.web.common.form.LoginForm;
import io.github.benas.todolist.web.common.util.TodoListUtils;
import io.github.todolist.core.domain.User;

/**
 * Action class that controls login/logout process.
 *
 * 
 */
public class SessionAction extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionAction.class.getName());

    private LoginForm loginForm;

    private String error;

    /**
     * ******
     * Login
     * *******
     */

    public String login() {
        return Action.SUCCESS;
    }

    public String doLogin() {
        if (userService.login(loginForm.getEmail(), loginForm.getPassword())) {
            User user = userService.getUserByEmail(loginForm.getEmail());
            session.put(TodoListUtils.SESSION_USER, user);
            return Action.SUCCESS;
        } else {
            error = getText("login.error.global.invalid");
            return Action.INPUT;
        }
    }

    /**
     * ******
     * Logout
     * *******
     */

    public String doLogout() {
        if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
            try {
                ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
            } catch (IllegalStateException e) {
                LOGGER.error("Unable to invalidate session.", e);
            }
        }
        return Action.SUCCESS;
    }

    /*
     * Getters and setters for model attributes
     */

    public LoginForm getLoginForm() {
        return loginForm;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }

    public String getLoginTabStyle() {
        return "active";
    }

    public String getError() {
        return error;
    }

}
