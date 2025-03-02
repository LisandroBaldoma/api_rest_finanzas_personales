package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.User.UserNotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserDao;
import com.apirest.finanzaspersonales.service.user.UserServiceQuery;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSeriviceQueryImpl implements UserServiceQuery {

    private final UserDao userDao;

    @Autowired
    public UserSeriviceQueryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean emailExists(String email) {
        return userDao.findByEmail(email) != null;
    }

    /* TODO: IMPLEMENTAR ROLE PARA LOS USUARIOS
    @Override
    public List<User> findUsersByRole(String role) {
        List<User> users = userDao.findAll();
        return users.stream()
                .filter(user -> user.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }
    */

    @Override
    public User getUserByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Usuario con correo " + email + " no encontrado.");
        }
        return user;
    }

    @Override
    public boolean existsById(int userId) {
        return userDao.findById(userId).isPresent();
    }

    @Override
    public long countUsers() {
        return userDao.findAll().size();
    }

    @Override
    public List<User> findByName(String name) {
        List<User> users = userDao.findAll();
        List<User> result = users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new UserNotFoundException("No se encontraron usuarios con el nombre: " + name);
        }

        return result;
    }
}

