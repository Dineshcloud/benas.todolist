

package io.github.todolist.core.service.api;

import io.github.todolist.core.domain.User;

/**
 * Business interface for user service.
 *
 * 
 */
public interface UserService {

    /**
     * Get user by email.
     *
     * @param email the user's email
     * @return the user with the given email or null if no such user
     */
    User getUserByEmail(final String email);

    /**
     * Check user's email and password.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return true if the credentials match, false else
     */
    boolean login(final String email, final String password);

    /**
     * Create a user.
     *
     * @param user the user to create
     * @return the created user
     */
    User create(final User user);

    /**
     * Update a user.
     *
     * @param user the user to update.
     * @return the updated user
     */
    User update(User user);

    /**
     * Remove a user.
     *
     * @param user the user to remove
     */
    void remove(final User user);
}
