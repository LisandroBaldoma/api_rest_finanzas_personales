package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.controller.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.User.UserNotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class UserSeriviceQueryImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserSeriviceQueryImpl userServiceQuery;

    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("licha@mail.com");
        user.setUserName("Licha");
        user.setPassword("password");

        userResponse = new UserResponse("Licha", "licha@mail.com");
    }

    @Test
    @DisplayName("Should return true if email exists in database")
    void emailExists_shouldReturnTrue_whenEmailIsFound() {
        when(userDao.findByEmail(user.getEmail())).thenReturn(user);

        boolean result = userServiceQuery.emailExists(user.getEmail());

        assertTrue(result);
        verify(userDao).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should return false if email doesn't exist in database")
    void emailExists_shouldReturnFalse_whenEmailIsNotFound() {
        when(userDao.findByEmail(anyString())).thenReturn(null);

        assertFalse(userServiceQuery.emailExists("notfound@mail.com"));
        verify(userDao).findByEmail("notfound@mail.com");
    }

    @Test
    @DisplayName("Should return UserResponse when email is found, otherwise throw exception")
    void getUserByEmail_shouldReturnUserResponse_whenEmailExists() {
        when(userDao.findByEmail(user.getEmail())).thenReturn(user);

        UserResponse result = userServiceQuery.getUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(userResponse.getEmail(), result.getEmail());
        verify(userDao).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user is not found by email")
    void getUserByEmail_shouldThrowException_whenUserNotFound() {
        when(userDao.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userServiceQuery.getUserByEmail("notfound@mail.com"));
        verify(userDao).findByEmail("notfound@mail.com");
    }

    @Test
    @DisplayName("Should return true if user ID exists")
    void existsById_shouldReturnTrue_whenUserExists() {
        when(userDao.findById(1)).thenReturn(Optional.of(user));

        assertTrue(userServiceQuery.existsById(1));
        verify(userDao).findById(1);
    }

    @Test
    @DisplayName("Should return false if user ID does not exist")
    void existsById_shouldReturnFalse_whenUserDoesNotExist() {
        when(userDao.findById(anyInt())).thenReturn(Optional.empty());

        assertFalse(userServiceQuery.existsById(99));
        verify(userDao).findById(99);
    }

    @Test
    @DisplayName("Should return total number of users in database")
    void countUsers_shouldReturnCorrectCount() {
        List<User> users = List.of(new User(), new User(), new User());

        when(userDao.findAll()).thenReturn(users);

        assertEquals(3, userServiceQuery.countUsers());
        verify(userDao).findAll();
    }

    @Test
    @DisplayName("Should return list of UserResponses matching given name")
    void findByName_shouldReturnUserResponses_whenUsersExist() {
        List<User> mockUsers = List.of(
                new User("John", "john1@example.com", "password"),
                new User("John", "john2@example.com", "password")
        );

        when(userDao.findAll()).thenReturn(mockUsers);

        List<UserResponse> result = userServiceQuery.findByName("John");

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getUsername());
        verify(userDao).findAll();
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when no users match the given name")
    void findByName_shouldThrowException_whenNoUsersFound() {
        when(userDao.findAll()).thenReturn(Collections.emptyList());

        assertThrows(UserNotFoundException.class, () -> userServiceQuery.findByName("NonExistent"));
        verify(userDao).findAll();
    }
}