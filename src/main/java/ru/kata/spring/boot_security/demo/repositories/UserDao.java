package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserDao {
    List<User> getUsersList();
    User getUser(long id);
    void addUser(User user);
    void deleteUser(long id);
    void editUser(User user);
    User findByUsername(String username);
}
