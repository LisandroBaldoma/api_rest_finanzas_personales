package com.apirest.finanzaspersonales.interfaces.user;

import com.apirest.finanzaspersonales.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceCrud {
    int addUser(User user);
    boolean removeUser(int userId);
    boolean updateUser(User user);
    Optional<User> getUserById(int userId);
    List<User> getAllUsers();
}
