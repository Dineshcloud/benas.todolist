
package io.github.benas.todolist.web.pages;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;

/**
 * 
 */
public class Error {

    @Persist(value = PersistenceConstants.FLASH)
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    /*
     * Setter needed to set error message
     */
    public void setErrorMessage(String error) {
        this.errorMessage = error;
    }
}
