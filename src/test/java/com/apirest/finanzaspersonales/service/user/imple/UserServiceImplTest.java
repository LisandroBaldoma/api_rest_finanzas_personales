package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.AlreadyExistsException;
import com.apirest.finanzaspersonales.exceptions.NotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserRepository;
import com.apirest.finanzaspersonales.utils.PasswordUtil;
import com.apirest.finanzaspersonales.utils.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userDao;

    @Mock
    private UserMapper userMapper; // Asegurar que está mockeado

    @Mock
    private PasswordUtil passwordUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserResponse userResponse;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordUtil.hashPassword(anyString())).thenReturn("hashedPassword");

        user = new User();
        user.setUserName("Licha");
        user.setEmail("licha@mail.com");
        user.setUserName("password");
        userResponse = new UserResponse("Licha", "licha@mail.com");
        userRequest = new UserRequest("Licha", "licha@mail.com", "password");
    }

    @Test
    @DisplayName("getUserById_ShouldReturnUserResponse")
    void getUserById_ShouldReturnUserResponse() {
        // Arrange
                User user = new User("John Doe", "john@example.com");
        UserResponse userResponse = new UserResponse("John Doe", "john@example.com");

        when(userDao.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.mapToUserResponse(user)).thenReturn(userResponse);

        // Act
        Optional<UserResponse> result = userService.getUserById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userResponse, result.get());
    }

    @Test
    @DisplayName("getUserById_ShouldThrowUserNotFoundException")
    void getUserById_ShouldThrowUserNotFoundException() {
        when(userDao.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.mapToUserResponse(user)).thenReturn(userResponse);

        Optional<UserResponse> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals("Licha", result.get().getUsername());
        assertEquals("licha@mail.com", result.get().getEmail());
        verify(userDao, times(1)).findById(1);
    }

    @Test
    @DisplayName("getAllUsers_ShouldReturnListOfUserResponses")
    void getAllUsers_ShouldReturnListOfUserResponses() {
        // Simular el comportamiento de userDao.findAll
        when(userDao.findAll()).thenReturn(List.of(user));

        // Simular el comportamiento de userMapper.mapToUserResponse
        when(userMapper.mapToUserResponse(user)).thenReturn(userResponse);

        // Llamar al método getAllUsers
        List<UserResponse> result = userService.getAllUsers();
        System.out.println("result " + result);

        // Verificaciones
        assertEquals(1, result.size());
        assertEquals("Licha", result.get(0).getUsername());
        assertEquals("licha@mail.com", result.get(0).getEmail());
        verify(userDao, times(1)).findAll();
        verify(userMapper, times(1)).mapToUserResponse(user);  // Verificar que el mapper fue llamado
    }

    @Test
    @DisplayName("registerUser_ShouldReturnUserResponse")
    void registerUser_ShouldReturnUserResponse() {
        // Simular el comportamiento de UserMapper
        when(userMapper.mapToUser(userRequest)).thenReturn(user); // Usar mapToUser
        when(userMapper.mapToUserResponse(user)).thenReturn(userResponse);

        // Simular el comportamiento de userDao.save
        doNothing().when(userDao).save(user);

        // Llamar al método registerUser
        UserResponse result = userService.registerUser(userRequest);

        // Verificaciones
        assertNotNull(result);
        assertEquals("Licha", result.getUsername());
        assertEquals("licha@mail.com", result.getEmail());
        verify(userMapper, times(1)).mapToUser(userRequest); // Usar mapToUser
        verify(userMapper, times(1)).mapToUserResponse(user);
        verify(userDao, times(1)).save(user);
    }

    @Test
    @DisplayName("registerUser_ShouldThrowEmailAlreadyExistsException")
    void registerUser_ShouldThrowEmailAlreadyExistsException() {
        // Simular que el correo ya está registrado
        when(userDao.findByEmail(userRequest.getEmail())).thenReturn(user);

        // Verificar que se lanza la excepción
        Exception exception = assertThrows(AlreadyExistsException.class, () -> userService.registerUser(userRequest));
        assertEquals("El correo ya está registrado.", exception.getMessage());

        // Verificar que se llamó a userDao.findByEmail
        verify(userDao, times(1)).findByEmail(userRequest.getEmail());
    }

    @Test
    @DisplayName("updateUser_ShouldThrowRuntimeException")
    void updateUser_ShouldThrowRuntimeException() {
        // Configurar el ID en userRequest
        userRequest.setId(99);

        // Simular que el usuario no existe
        when(userDao.findById(99)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción
        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUser(userRequest));
        assertEquals("Usuario no encontrado con ID: 99", exception.getMessage());

        // Verificar que se llamó a userDao.findById
        verify(userDao, times(1)).findById(99);
    }

    @Test
    @DisplayName("removeUser_ShouldDeleteUser")
    void removeUser_ShouldDeleteUser() {
        // Simular que el usuario existe y se elimina correctamente
        when(userDao.delete(1)).thenReturn(true);

        // Llamar al método removeUser
        userService.removeUser(1);

        // Verificar que se llamó a userDao.delete
        verify(userDao, times(1)).delete(1);
    }

    @Test
    @DisplayName("removeUser_ShouldThrowUserNotFoundException")
    void removeUser_ShouldThrowUserNotFoundException() {
        // Simular que el usuario no existe
        when(userDao.delete(1)).thenReturn(false);

        // Verificar que se lanza la excepción
        Exception exception = assertThrows(NotFoundException.class, () -> userService.removeUser(1));
        assertEquals("Usuario con ID 1 no encontrado.", exception.getMessage());

        // Verificar que se llamó a userDao.delete
        verify(userDao, times(1)).delete(1);
    }

}