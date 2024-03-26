package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;


public interface UserService {

    User findByUsername (String name);
    void addUser(User user);
    void removeUser(long id);
    List<User> userList();
    User getOneUser(long id);
    void updateUser(User user);
}
