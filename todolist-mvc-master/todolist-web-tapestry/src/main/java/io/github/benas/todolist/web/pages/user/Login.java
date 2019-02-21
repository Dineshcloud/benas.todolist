

package io.github.benas.todolist.web.pages.user;

import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.UserService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * 
 */
@SuppressWarnings("unused")
public class Login {

    @Inject
    private UserService userService;

    @SessionState
    private User loggedUser;

    @Inject
    private Messages messages;

    @Property
    private String email;

    @Property
    private String password;

    @Property
    private String error;

    @Property
    @Component
    private Form loginForm;

    @OnEvent(value = EventConstants.VALIDATE, component = "loginForm")
    public void validateLoginForm() {
        if (!userService.login(email, password)) {
            loginForm.recordError(messages.get("login.error.global.invalid"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "loginForm")
    public Object onSuccess() {
        loggedUser = userService.getUserByEmail(email);
        return Home.class;
    }

}
