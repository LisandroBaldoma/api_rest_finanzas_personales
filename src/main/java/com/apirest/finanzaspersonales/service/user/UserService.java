package com.apirest.finanzaspersonales.service.user;

import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserResponse> getUserById(Long userId);

    List<UserResponse> getAllUsers();

    UserResponse registerUser(UserRequest userRequest);

    Optional<UserResponse> updateUser(Long userId, UserRequest userRequest);

    void removeUser(Long userId);

}
