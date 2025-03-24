package com.apirest.finanzaspersonales.service.user;

import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;

import java.util.List;

public interface UserServiceQuery {
    boolean emailExists(String email);
    //List<User> findUsersByRole(String role);
    UserResponse getUserByEmail(String email);
    boolean existsById(int userId);
    long countUsers();
    List<UserResponse> findByName(String name);

}
