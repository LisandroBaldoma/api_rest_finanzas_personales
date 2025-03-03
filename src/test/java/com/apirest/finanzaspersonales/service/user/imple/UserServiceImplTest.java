package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.User.UserNotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserDao;
import com.apirest.finanzaspersonales.repository.user.imple.UserDaoListImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setUserName("Licha");
        user.setEmail("licha@mail.com");
        user.setPassword("password");
    }

    @Test
    @DisplayName("getUserById_ShouldReturnUser")
    void getUserById_ShouldReturnUser() {
        // GIVEN
        when(userDao.findById(1)).thenReturn(Optional.of(user));

        // WHEN
        Optional<User> result = userService.getUserById(1);

        // THEN
        assertTrue(result.isPresent());
        assertEquals("Licha", result.get().getUsername());
        verify(userDao, times(1)).findById(1);
    }

    @Test
    @DisplayName("getUserById_ShouldThrowExceptionIfNotFound")
    void getUserById_ShouldThrowExceptionIfNotFound() {
        // GIVEN
        when(userDao.findById(1)).thenReturn(Optional.empty());

        // WHEN
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));

        // THEN
        assertEquals("Usuario con ID 1 no encontrado", exception.getMessage());
        verify(userDao, times(1)).findById(1);
    }

    @Test
    @DisplayName("getAllUsers_ShouldReturnListOfUsers")
    void getAllUsers_ShouldReturnListOfUsers() {
        // GIVEN
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userDao.findAll()).thenReturn(users);

        // WHEN
        List<User> result = userService.getAllUsers();

        // THEN
        assertEquals(1, result.size());
        assertEquals("Licha", result.get(0).getUsername());
        verify(userDao, times(1)).findAll();
    }

    @Test
    @DisplayName("registerUser_ShouldSaveUser")
    void registerUser_ShouldSaveUser() {
        // GIVEN
        when(userDao.findByEmail(user.getEmail())).thenReturn(null);
        //when(userDao.save(any(User.class))).thenReturn(user);
        doNothing().when(userDao).save(any(User.class));

        // WHEN
        User result = userService.registerUser(user);

        // THEN
        assertNotNull(result);
        assertEquals("Licha", result.getUsername());
        verify(userDao, times(1)).findByEmail(user.getEmail());
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("updateUser_ShouldUpdateUser")
    void updateUser_ShouldUpdateUser() {
        // GIVEN
        when(userDao.findById(1)).thenReturn(Optional.of(user));

        // WHEN
        Optional<User> result = userService.updateUser(user);

        // THEN
        assertTrue(result.isPresent());
        assertEquals("Licha", result.get().getUsername());
        verify(userDao, times(1)).findById(1);
        verify(userDao, times(1)).update(user);
    }

    @Test
    @DisplayName("removeUser_ShouldThrowExceptionIfNotFound")
    void removeUser_ShouldThrowExceptionIfNotFound() {
        // GIVEN
        when(userDao.delete(1)).thenReturn(false);

        // WHEN
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.removeUser(1));

        // THEN
        assertEquals("Usuario con ID 1 no encontrado.", exception.getMessage());
        verify(userDao, times(1)).delete(1);

    }

    @Test
    @DisplayName("removeUser_ShouldDeleteUser")
    void removeUser_ShouldDeleteUser() {
        // GIVEN
        when(userDao.delete(1)).thenReturn(true);

        // WHEN
        userService.removeUser(1);

        // THEN
        verify(userDao, times(1)).delete(1);
    }



}