package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.AlreadyExistsException;
import com.apirest.finanzaspersonales.exceptions.NotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserRepository;
import com.apirest.finanzaspersonales.utils.PasswordUtil;
import com.apirest.finanzaspersonales.utils.mappers.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordUtil passwordUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("getUserById")
    void getUserById_ShouldReturnUserResponse_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User(userId, "John Doe", "john@example.com", "password123");
        UserResponse mockResponse = new UserResponse("John Doe", "john@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userMapper.mapToUserResponse(mockUser)).thenReturn(mockResponse);

        // Act
        Optional<UserResponse> result = userService.getUserById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockResponse, result.get());
        verify(userRepository).findById(userId);
        verify(userMapper).mapToUserResponse(mockUser);
    }

    @Test
    @DisplayName("getUserById NotFound")
    void getUserById_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
        assertEquals("Usuario con ID " + userId + " no encontrado", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("getAllUsers")
    void getAllUsers_ShouldReturnListOfUserResponses() {
        // Arrange
        List<User> mockUsers = List.of(
                new User(1L, "John Doe", "john@example.com", "password"),
                new User(2L, "Jane Doe", "jane@example.com", "password")
        );
        List<UserResponse> mockResponses = List.of(
                new UserResponse("John Doe", "john@example.com"),
                new UserResponse("Jane Doe", "jane@example.com")
        );

        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userMapper.mapToUserResponse(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            return new UserResponse(user.getName(), user.getEmail());
        });

        // Act
        List<UserResponse> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void registerUser_ShouldCreateNewUser_WhenEmailIsUnique() {
        // Arrange
        UserRequest request = new UserRequest("John Doe", "john@example.com", "password123");
        User user = new User(null, "John Doe", "john@example.com", "password123");
        UserResponse response = new UserResponse("John Doe", "john@example.com");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(null);
        when(userMapper.mapToUser(request)).thenReturn(user);
        when(passwordUtil.hashPassword(request.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });
        when(userMapper.mapToUserResponse(any(User.class))).thenReturn(response);

        // Act
        UserResponse result = userService.registerUser(request);

        // Assert
        assertEquals(response, result);
        verify(userRepository).findByEmail(request.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        UserRequest request = new UserRequest("John Doe", "john@example.com", "password123");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(new User());

        // Act & Assert
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> userService.registerUser(request));
        assertEquals("El correo ya está registrado.", exception.getMessage());
        verify(userRepository).findByEmail(request.getEmail());
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenDataIsValid() {
        // Arrange
        Long userId = 1L;
        UserRequest request = new UserRequest("Updated Name", "newemail@example.com", "newpassword");
        User existingUser = new User(userId, "John Doe", "john@example.com", "oldpassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordUtil.hashPassword(request.getPassword())).thenReturn("hashedNewPassword");

        // Act
        UserResponse result = userService.updateUser(userId, request);

        // Assert
        assertEquals(request.getName(), existingUser.getName());
        assertEquals(request.getEmail(), existingUser.getEmail());
        assertEquals("hashedNewPassword", existingUser.getPassword());
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        Long userId = 99L;
        UserRequest request = new UserRequest("Updated Name", "newemail@example.com", "newpassword");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.updateUser(userId, request));
        assertEquals("Usuario no encontrado con ID: " + userId, exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void updateUser_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        Long userId = 1L;
        UserRequest request = new UserRequest("Updated Name", "existingemail@example.com", "newpassword");
        User existingUser = new User(userId, "John Doe", "john@example.com", "oldpassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act & Assert
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> userService.updateUser(userId, request));
        assertEquals("El correo electrónico ya está registrado.", exception.getMessage());
        verify(userRepository).findById(userId);
        verify(userRepository).existsByEmail(request.getEmail());
    }

    @Test
    void removeUser_ShouldDeleteUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        userService.removeUser(userId);

        // Assert
        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void removeUser_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        Long userId = 99L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.removeUser(userId));
        assertEquals("Usuario con ID " + userId + " no encontrado.", exception.getMessage());
        verify(userRepository).existsById(userId);
    }
}