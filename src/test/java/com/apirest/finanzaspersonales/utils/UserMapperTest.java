package com.apirest.finanzaspersonales.utils;

import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper; // Instancia de UserMapper

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper(); // Inicializar UserMapper
    }

    @Test
    void shouldMapUserRequestToUser() {
        // Given
        UserRequest request = new UserRequest();
        request.setId(1);
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        // When
        User user = userMapper.mapToUser(request); // Usar la instancia de UserMapper

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    void shouldMapUserRequestToUserUpdate() {
        // Given
        UserRequest request = new UserRequest();
        request.setId(1);
        request.setUsername("updatedUser");
        request.setEmail("updated@example.com");
        request.setPassword("newPassword");

        // When
        User updatedUser = userMapper.maptoUserUpdate(request); // Usar la instancia de UserMapper

        // Then
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(1);
        assertThat(updatedUser.getUsername()).isEqualTo("updatedUser");
        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
        assertThat(updatedUser.getPassword()).isEqualTo("newPassword");
    }

    @Test
    void shouldMapUserToUserResponse() {
        // Given
        User user = new User();
        user.setUserName("responseUser");
        user.setEmail("response@example.com");

        // When
        UserResponse response = userMapper.mapToUserResponse(user); // Usar la instancia de UserMapper

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("responseUser");
        assertThat(response.getEmail()).isEqualTo("response@example.com");
    }
}