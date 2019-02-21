

package io.github.benas.todolist.web.pages;

import io.github.todolist.core.domain.User;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

/**
 * 
 */
public class Index {

    @SessionState
    private User loggedUser;

    @Property
    private boolean loggedUserExists;

}
