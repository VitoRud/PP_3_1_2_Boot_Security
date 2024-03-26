package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserDao userDao;

    @Autowired
    public void setUserRepository(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public User findByUsername (String name) {
        return userDao.findByUsername(name);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = findByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),mapRolesToAuthorities(user.getRoles()) );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities (Collection<Role> roles) {
        return roles.stream().map(a -> new SimpleGrantedAuthority(a.getName())).collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    public List<User> userList() {
        return userDao.getUsersList();
    }

    @Override
    public User getOneUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.editUser(user);
    }
}
