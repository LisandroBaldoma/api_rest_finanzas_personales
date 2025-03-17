package com.apirest.finanzaspersonales.service.user;

import com.apirest.finanzaspersonales.controller.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;

import java.util.List;

public interface UserServiceQuery {
    boolean emailExists(String email);
    //List<User> findUsersByRole(String role);
    UserResponse getUserByEmail(String email);
    boolean existsById(int userId);
    long countUsers();
    List<UserResponse> findByName(String name);

}
