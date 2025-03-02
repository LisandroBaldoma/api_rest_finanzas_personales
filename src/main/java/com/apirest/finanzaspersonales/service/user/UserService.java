package com.apirest.finanzaspersonales.service.user;

import com.apirest.finanzaspersonales.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);
    void removeUser(int userId);
    Optional<User> updateUser(User user);
    Optional<User> getUserById(int userId);
    List<User> getAllUsers();
    User registerUser(User user);

}
