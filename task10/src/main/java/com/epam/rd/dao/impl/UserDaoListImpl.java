package com.epam.rd.dao.impl;

import com.epam.rd.dao.IUserDao;
import com.epam.rd.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * IUserDao implementation based on the constant List of Users
 */
public class UserDaoListImpl implements IUserDao {
    private final List<User> storage = new ArrayList<>(Arrays.asList(
            new User(4, "racoon24", "Oleg", "Enotov", "racoon24@gmail.com", null),
            new User(17, "asdf", "Jon", "Smith", "smith_mail@gmail.com", null)
    ));

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
    public User findByLogin(String login) {
        return findByPredicate.apply(user -> user.getLogin().equalsIgnoreCase(login));
    }

    @Override
    public User findByEmail(String email) {
        return findByPredicate.apply(user -> user.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public boolean add(User newUser) {
        // another check control data
        for (User user : storage) {
            if (newUser.getId() == user.getId() ||
                    newUser.getLogin().equals(user.getLogin()) ||
                    newUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                return false;
            }
        }

        // generate new id (not 0)
        if (newUser.getId() == 0) {
            int id = Optional.ofNullable(storage.get(storage.size() - 1))
                    .map(user -> user.getId() + 1)
                    .orElse(1);
            newUser.setId(id);
        }

        storage.add(newUser);
        return true;
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
