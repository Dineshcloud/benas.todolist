

package io.github.todolist.core.repository.impl;

import io.github.todolist.core.domain.User;
import io.github.todolist.core.repository.api.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of {@link UserRepository} using JPA.
 *
 * 
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    public User create(final User user) {
        entityManager.persist(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    public User update(User user) {
        return entityManager.merge(user);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(final User user) {
        entityManager.createNativeQuery("DELETE FROM todo t WHERE t.userId = " + user.getId()).executeUpdate();
        User u = entityManager.find(User.class, user.getId());
        entityManager.remove(u);
    }

    /**
     * {@inheritDoc}
     */
    public User getUserByEmail(final String email) {
        TypedQuery<User> query = entityManager.createNamedQuery("findUserByEmail", User.class);
        query.setParameter("p_email", email);
        List<User> users = query.getResultList();
        return (users != null && !users.isEmpty()) ? users.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean login(final String email, final String password) {
        TypedQuery<User> query = entityManager.createNamedQuery("findUserByEmailAndPassword", User.class);
        query.setParameter("p_email", email);
        query.setParameter("p_password", password);
        List<User> users = query.getResultList();
        return (users != null && !users.isEmpty());
    }

}
