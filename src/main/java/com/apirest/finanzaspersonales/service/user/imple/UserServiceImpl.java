package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.exceptions.AlreadyExistsException;
import com.apirest.finanzaspersonales.exceptions.NotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserRepository;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.service.user.UserService;
import com.apirest.finanzaspersonales.utils.PasswordUtil;
import com.apirest.finanzaspersonales.utils.UserMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordUtil passwordUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordUtil passwordUtil){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public Optional<UserResponse> getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapToUserResponse)
                .or(() -> {
                    throw new NotFoundException("Usuario con ID " + userId + " no encontrado");
                });
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToUserResponse)
                .toList();
    }

    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new AlreadyExistsException("El correo ya está registrado.");
        }

        // Mapear el request a la entidad User
        User user = userMapper.mapToUser(userRequest);

        // Hashear la contraseña
        String hashedPassword = passwordUtil.hashPassword(userRequest.getPassword());
        user.setPassword(hashedPassword);

        // Guardar usuario
        userRepository.save(user);

        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long userId, @Valid UserRequest userRequest) {
        // Buscar usuario o lanzar excepción si no existe
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + userId));

        // Si el nombre está presente en la petición, actualizarlo
        Optional.ofNullable(userRequest.getName()).ifPresent(userToUpdate::setName);

        // Si el email está presente y es diferente, actualizarlo
        Optional.ofNullable(userRequest.getEmail()).ifPresent(email -> {
            if (!email.equals(userToUpdate.getEmail()) && userRepository.existsByEmail(email)) {
                throw new AlreadyExistsException("El correo electrónico ya está registrado.");
            }
            userToUpdate.setEmail(email);
        });

        // Si la contraseña está presente, actualizarla
        Optional.ofNullable(userRequest.getPassword()).ifPresent(password -> {
            String hashedPassword = passwordUtil.hashPassword(password);
            userToUpdate.setPassword(hashedPassword);
        });

        // Guardar cambios
        userRepository.save(userToUpdate);

        return userMapper.mapToUserResponse(userToUpdate);
    }

    @Override
    public void removeUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuario con ID " + userId + " no encontrado.");
        }
        userRepository.deleteById(userId);
    }
}
