package com.apirest.finanzaspersonales.utils;

import com.apirest.finanzaspersonales.controller.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    // Convertir UserRequestDTO -> User
    public User maptoUser(UserRequest userRequest) {
        User user = new User();
        user.setUserName(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword()); // Solo se usa en el registro
        return user;
    }
    public User maptoUserUpdate(UserRequest userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setUserName(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword()); // Solo se usa en el registro
        return user;
    }

    public UserResponse mapToUserResponse(User user){
        return new UserResponse(user.getUsername(), user.getEmail());
    }
}
