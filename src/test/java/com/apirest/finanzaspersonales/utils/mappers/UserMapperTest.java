package com.apirest.finanzaspersonales.utils.mappers;

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

        request.setName("testUser");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        // When
        User user = userMapper.mapToUser(request); // Usar la instancia de UserMapper

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("testUser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    void shouldMapUserRequestToUserUpdate() {
        // Given
        UserRequest request = new UserRequest("updatedUser", "updated@example.com","newPassword");

        // When
        User updatedUser = userMapper.mapToUserUpdate(request); // Usar la instancia de UserMapper

        // Then
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("updatedUser");
        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
    }

    @Test
    void shouldMapUserToUserResponse() {
        // Given
        User user = new User();
        user.setName("responseUser");
        user.setEmail("response@example.com");

        // When
        UserResponse response = userMapper.mapToUserResponse(user); // Usar la instancia de UserMapper

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("responseUser");
        assertThat(response.getEmail()).isEqualTo("response@example.com");
    }
}