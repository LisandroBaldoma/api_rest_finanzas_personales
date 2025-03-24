package com.apirest.finanzaspersonales.utils;

import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    // Convertir UserRequestDTO -> User
    public User mapToUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword()); // Solo se usa en el registro
        return user;
    }
    public User mapToUserUpdate(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        return user;
    }

    public UserResponse mapToUserResponse(User user){
        return new UserResponse(user.getName(), user.getEmail());
    }
}
