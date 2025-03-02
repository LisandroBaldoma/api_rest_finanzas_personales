package com.apirest.finanzaspersonales.service.user;

import com.apirest.finanzaspersonales.entity.User;

import java.util.List;

public interface UserServiceQuery {
    boolean emailExists(String email);
    //List<User> findUsersByRole(String role);
    User getUserByEmail(String email);
    boolean existsById(int userId);
    long countUsers();
    List<User> findByName(String name);

}
