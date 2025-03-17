package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.controller.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.model.response.UserResponse;
import com.apirest.finanzaspersonales.exceptions.User.EmailAlreadyExistsException;
import com.apirest.finanzaspersonales.exceptions.User.UserNotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserDao;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.service.user.UserService;
import com.apirest.finanzaspersonales.utils.PasswordUtil;
import com.apirest.finanzaspersonales.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final PasswordUtil passwordUtil;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper userMapper, PasswordUtil passwordUtil){
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public Optional<UserResponse> getUserById(int userId) {
        return userDao.findById(userId)
                .map(userMapper::mapToUserResponse)
                .or(() -> {
                    throw new UserNotFoundException("Usuario con ID " + userId + " no encontrado");
                });
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userDao.findAll().stream()
                .map(userMapper::mapToUserResponse)
                .toList();
    }

    @Override
    public UserResponse registerUser(UserRequest userRequest) {

        if (userDao.findByEmail(userRequest.getEmail()) != null) {
            throw new EmailAlreadyExistsException("El correo ya está registrado.");
        }

        User user = userMapper.maptoUser(userRequest);

        String hashedPassword = passwordUtil.hashPassword(userRequest.getPassword());
        user.setPassword(hashedPassword);
        userDao.save(user);

        return userMapper.mapToUserResponse(user);
    }

    @Override
    public Optional<UserResponse> updateUser(UserRequest userRequest) {
        Optional<User> existingUser = userDao.findById(userRequest.getId());
        if (existingUser.isPresent()) {
            User updatedUser = userMapper.maptoUserUpdate(userRequest); // Convertir DTO a entidad
            userDao.update(updatedUser);
            return userDao.findById(userRequest.getId()).map(userMapper::mapToUserResponse);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + userRequest.getId());
    }

    @Override
    public void removeUser(int userId) {
        if (!userDao.delete(userId)) {
            throw new UserNotFoundException("Usuario con ID " + userId + " no encontrado.");
        }
    }

}
