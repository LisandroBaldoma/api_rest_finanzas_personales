package com.apirest.finanzaspersonales.service.user;

import com.apirest.finanzaspersonales.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(int userId);
    List<User> getAllUsers();
    User registerUser(User user);
    Optional<User> updateUser(User user);
    void removeUser(int userId);

}
