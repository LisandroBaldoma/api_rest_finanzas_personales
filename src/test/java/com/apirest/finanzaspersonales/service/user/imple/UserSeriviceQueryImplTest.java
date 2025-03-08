package com.apirest.finanzaspersonales.service.user.imple;

import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.User.UserNotFoundException;
import com.apirest.finanzaspersonales.repository.user.UserDao;
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


    @Test
    @DisplayName("Should return true if email exists in database")
    void emailExists_shouldReturnTrue_whenEmailIsFound() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        when(userDao.findByEmail(email)).thenReturn(mockUser);

        // Act
        boolean result = userServiceQuery.emailExists(email);

        // Assert
        assertTrue(result);
        verify(userDao).findByEmail(email);
    }

    @Test
    @DisplayName("Should return null if email don't exists in database")
    void emailExists_shouldReturnFalse_whenEmailIsNotFound() {
        when(userDao.findByEmail(anyString())).thenReturn(null);

        assertFalse(userServiceQuery.emailExists("notfound@example.com"));
        verify(userDao).findByEmail("notfound@example.com");
    }

    @Test
    @DisplayName("Should return user when email is found, otherwise throw exception")
    void getUserByEmail_shouldReturnUser_whenEmailExists() {
        String email = "user@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        when(userDao.findByEmail(email)).thenReturn(mockUser);

        User result = userServiceQuery.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userDao).findByEmail(email);
    }

    @Test
    void getUserByEmail_shouldThrowException_whenUserNotFound() {
        String email = "notfound@example.com";

        when(userDao.findByEmail(email)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userServiceQuery.getUserByEmail(email));
        verify(userDao).findByEmail(email);
    }

    @Test
    @DisplayName("Should return true if user ID exists")
    void existsById_shouldReturnTrue_whenUserExists() {
        int userId = 1;

        when(userDao.findById(userId)).thenReturn(Optional.of(new User()));

        assertTrue(userServiceQuery.existsById(userId));
        verify(userDao).findById(userId);
    }

    @Test
    void existsById_shouldReturnFalse_whenUserDoesNotExist() {
        when(userDao.findById(anyInt())).thenReturn(Optional.empty());

        assertFalse(userServiceQuery.existsById(99));
        verify(userDao).findById(99);
    }

    @Test
    @DisplayName("Should return total number of users in database")
    void countUsers_shouldReturnCorrectCount() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        when(userDao.findAll()).thenReturn(users);

        assertEquals(3, userServiceQuery.countUsers());
        verify(userDao).findAll();
    }

    @Test
    @DisplayName("Should return users matching given name, otherwise throw exception")
    void findByName_shouldReturnUsers_whenUsersExist() {
        String name = "John";
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("John", "admin", "john@example.com"));
        mockUsers.add(new User("John", "admin", "john@example.com"));

        when(userDao.findAll()).thenReturn(mockUsers);

        List<User> result = userServiceQuery.findByName(name);

        assertEquals(2, result.size());
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