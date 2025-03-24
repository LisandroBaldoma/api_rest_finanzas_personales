package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.exceptions.EmailAlreadyExistsException;
import com.apirest.finanzaspersonales.exceptions.BadRequestException;
import com.apirest.finanzaspersonales.exceptions.NotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserRepository;
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
        // Verificar si ya existe un usuario con el mismo correo
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new EmailAlreadyExistsException("El correo ya está registrado.");
        }
        if (userRequest.getName() == null || userRequest.getName().trim().isEmpty()) {
            throw new BadRequestException("El nombre es obligatorio");
        }

        if (userRequest.getEmail() == null || userRequest.getEmail().trim().isEmpty()) {
            throw new BadRequestException("El email es obligatorio");
        }

        if (userRequest.getPassword() == null || userRequest.getPassword().trim().isEmpty()) {
            throw new BadRequestException("La contraseña es obligatoria en el registro");
        }

        // Mapear el UserRequest a un objeto User
        User user = userMapper.mapToUser(userRequest);

        // Hashear la contraseña antes de guardarla
        // Validar la contraseña según los requisitos de seguridad (si lo deseas)
        if (userRequest.getPassword().length() < 3) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 3 caracteres.");
        }
        String hashedPassword = passwordUtil.hashPassword(userRequest.getPassword());
        user.setPassword(hashedPassword);

        // Guardar el nuevo usuario en la base de datos
        userRepository.save(user);

        // Mapear el usuario guardado a un UserResponse
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public Optional<UserResponse> updateUser(Long userId, UserRequest userRequest) {

        // Buscar el usuario por ID
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();

            // Si el nombre está presente en la petición, actualizarlo
            if (userRequest.getName() != null && !userRequest.getName().isEmpty()) {
                userToUpdate.setName(userRequest.getName());
            }

            // Si el email está presente en la petición, actualizarlo
            if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) {
                // Verificar que el nuevo email no esté en uso
                if (userRepository.existsByEmail(userRequest.getEmail())) {
                    throw new IllegalArgumentException("El correo electrónico ya está registrado.");
                }
                userToUpdate.setEmail(userRequest.getEmail());
            }

            // Si la contraseña está presente en la petición, actualizarla
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                // Validar la contraseña según los requisitos de seguridad (si lo deseas)
                if (userRequest.getPassword().length() < 3) {
                    throw new IllegalArgumentException("La contraseña debe tener al menos 3 caracteres.");
                }
                String hashedPassword = passwordUtil.hashPassword(userRequest.getPassword());
                userToUpdate.setPassword(hashedPassword);
            }

            // Guardar el usuario actualizado en la base de datos
            userRepository.save(userToUpdate);

            // Retornar la respuesta mapeada con los datos del usuario actualizado
            return Optional.of(userMapper.mapToUserResponse(userToUpdate));
        }

        // Si no se encuentra el usuario
        throw new NotFoundException("Usuario no encontrado con ID: " + userId);
    }

    @Override
    public void removeUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuario con ID " + userId + " no encontrado.");
        }
        userRepository.deleteById(userId);
    }
}
