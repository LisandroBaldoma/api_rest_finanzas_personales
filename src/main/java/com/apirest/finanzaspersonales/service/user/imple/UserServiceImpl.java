package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.exceptions.User.EmailAlreadyExistsException;
import com.apirest.finanzaspersonales.exceptions.User.UserNotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserDao;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.service.user.UserService;
import com.apirest.finanzaspersonales.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final List<User> users = new ArrayList<>();

    @Autowired
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public Optional<User> getUserById(int userId) {
        return Optional.ofNullable(userDao.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID " + userId + " no encontrado")));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User registerUser(User user) {
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("El correo ya está registrado.");
        }

        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userDao.save(user);

        return user;
    }

    @Override
    public Optional<User> updateUser(User user) {
        Optional<User> existingUser = userDao.findById(user.getId());
        if (existingUser.isPresent()) {
            userDao.update(user);
            return Optional.of(user);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + user.getId());
    }

    @Override
    public void removeUser(int userId) {
        if (!userDao.delete(userId)) {
            throw new UserNotFoundException("Usuario con ID " + userId + " no encontrado.");
        }
    }




}
