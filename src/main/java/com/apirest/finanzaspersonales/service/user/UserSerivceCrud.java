package com.apirest.finanzaspersonales.service.user;

import com.apirest.finanzaspersonales.repository.imple.user.UserDao;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.interfaces.user.UserServiceCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSerivceCrud implements UserServiceCrud {

    UserDao userDao;

    @Autowired
    public UserSerivceCrud(UserDao userDao){
        this.userDao = userDao;
    }
    private final List<User> users = new ArrayList<>();

    @Override
    public int  addUser(User user) {
        return userDao.save(user);
    }

    @Override
    public boolean removeUser(int userId) {
       return userDao.delete(userId);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    public Optional<User> getUserById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
