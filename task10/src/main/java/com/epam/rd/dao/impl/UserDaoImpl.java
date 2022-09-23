package com.epam.rd.dao.impl;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanName;
import com.epam.rd.dao.IUserDao;
import com.epam.rd.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * IUserDao implementation based on the constant List of Users
 */
public class UserDaoImpl implements IUserDao {
    private final List<User> storage = (ArrayList<User>) ApplicationContext.getInstance().getAttribute(BeanName.USERS);

    @Override
    public List<User> findAll() {
        return storage;
    }

    private Function<Predicate<User>, User> findByPredicate = predicate ->
            storage.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);

    @Override
    public User findById(int id) {
        return findByPredicate.apply(user -> user.getId() == id);
    }

    @Override
    public User findByUsername(String login) {
        return findByPredicate.apply(user -> user.getUsername().equalsIgnoreCase(login));
    }

    @Override
    public User findByEmail(String email) {
        return findByPredicate.apply(user -> user.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public boolean add(User newUser) {
        // generate new id (not 0)
        if (storage.isEmpty()) {
            newUser.setId(1);
        } else if (newUser.getId() == 0) {
            int id = storage.get(storage.size() - 1).getId() + 1;
            newUser.setId(id);
        }

        return storage.add(newUser);
    }

    @Override
    public boolean removeById(int id) {
        for (User user : storage) {
            if (user.getId() == id) {
                return storage.remove(user);
            }
        }

        return false;
    }
}
