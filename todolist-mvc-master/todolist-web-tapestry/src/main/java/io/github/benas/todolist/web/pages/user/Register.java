

package io.github.benas.todolist.web.pages.user;

import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.UserService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
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
public class Register {

    @SessionState
    private User loggedUser;

    @Inject
    private UserService userService;

    @Inject
    private Messages messages;

    @Property
    private String name;

    @Property
    private String email;

    @Property
    private String password;

    @Property
    private String confirmationPassword;

    @Property
    private String error;

    @Property
    @InjectComponent
    private Form registerForm;

    @OnEvent(value = EventConstants.VALIDATE, component = "registerForm")
    public void validateForm() {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            registerForm.recordError(messages.format("register.error.global.account", email));
        }
        if (!password.equals(confirmationPassword)) {
            registerForm.recordError(messages.get("register.error.password.confirmation.error"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "registerForm")
    public Object onRegisterSuccess() {
        User user = new User(name, email, password);
        userService.create(user);
        loggedUser = userService.getUserByEmail(email);
        return Home.class;
    }

}
