package com.apirest.finanzaspersonales.service.user;

import com.apirest.finanzaspersonales.controller.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserResponse> getUserById(int userId);
    List<UserResponse> getAllUsers();
    UserResponse registerUser(UserRequest userDto);
    Optional<UserResponse> updateUser(UserRequest user);
    void removeUser(int userId);

}
