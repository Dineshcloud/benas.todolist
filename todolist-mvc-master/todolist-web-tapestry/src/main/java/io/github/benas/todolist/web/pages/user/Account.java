

package io.github.benas.todolist.web.pages.user;

import io.github.benas.todolist.web.pages.Index;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.TodoService;
import io.github.todolist.core.service.api.UserService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * 
 */
@SuppressWarnings("unused")
public class Account {

    @Inject
    private UserService userService;

    @Inject
    private TodoService todoService;

    @Inject
    private Request request;

    @Inject
    private Messages messages;

    @Property
    @SessionState
    private User loggedUser;

    @Property
    private Long id;

    @Property
    private String name;

    @Property
    private String email;

    @Property
    @Component
    private Form changePasswordForm;

    @Property
    @Component
    private Form updateAccountForm;

    @Property
    private String error;

    @Property
    private String currentPassword;

    @Property
    private String newPassword;

    @Property
    private String confirmationPassword;

    @Property
    @Persist(value = PersistenceConstants.FLASH)
    private String updateProfileSuccessMessage;

    @Property
    @Persist(value = PersistenceConstants.FLASH)
    private String updatePasswordSuccessMessage;

    @OnEvent(value = EventConstants.ACTIVATE)
    public void init() {
        id = loggedUser.getId();
        name = loggedUser.getName();
        email = loggedUser.getEmail();
    }

    /*
     * Update account
     */

    @OnEvent(value = EventConstants.VALIDATE, component = "updateAccountForm")
    public void validateUpdateAccountForm() {
        if (isAlreadyUsed(email) && isDifferent(email)) {
            updateAccountForm.recordError(messages.format("account.email.alreadyUsed", email));
        }
    }

    private boolean isDifferent(String email) {
        return !email.equals(loggedUser.getEmail());
    }

    private boolean isAlreadyUsed(String email) {
        return userService.getUserByEmail(email) != null;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "updateAccountForm")
    public Object updateAccount() {
        loggedUser.setName(name);
        loggedUser.setEmail(email);
        userService.update(loggedUser);
        updateProfileSuccessMessage = messages.get("account.profile.update.success");
        return null;
    }

    /*
     * Change password
     */

    @OnEvent(value = EventConstants.VALIDATE, component = "changePasswordForm")
    public void validateChangePasswordForm() {
        if (isIncorrect(currentPassword)) {
            changePasswordForm.recordError(messages.get("account.password.error"));
        } else {
            if (!newPassword.equals(confirmationPassword))
                changePasswordForm.recordError(messages.get("account.password.confirmation.error"));
        }
    }

    private boolean isIncorrect(String password) {
        return !password.equals(loggedUser.getPassword());
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "changePasswordForm")
    public Object changePassword() {
        loggedUser.setPassword(newPassword);
        userService.update(loggedUser);
        updatePasswordSuccessMessage = messages.get("account.password.update.success");
        return null;
    }

    /*
     * Change password
     */

    @OnEvent(value = EventConstants.ACTION, component = "deleteAccountLink")
    public Object deleteAccount() {
        userService.remove(loggedUser);
        request.getSession(false).invalidate();
        loggedUser = null;
        return Index.class;
    }

}
