package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.NotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserRepository;
import com.apirest.finanzaspersonales.service.user.UserServiceQuery;
import com.apirest.finanzaspersonales.utils.mappers.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceQueryImpl implements UserServiceQuery {

    private final UserRepository userDao;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceQueryImpl(UserRepository userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
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
    public UserResponse getUserByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("Usuario con correo " + email + " no encontrado.");
        }
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public boolean existsById(int userId) {
        return false;
    }


    @Override
    public long countUsers() {
        return userDao.findAll().size();
    }

    @Override
    public List<UserResponse> findByName(String name) {
        List<User> users = userDao.findAll();
        System.out.println("Usuarios obtenidos: " + users);
        List<User> result = users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .toList();

        if (result.isEmpty()) {
            throw new NotFoundException("No se encontraron usuarios con el nombre: " + name);
        }

        return result.stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

}

