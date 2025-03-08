package com.apirest.finanzaspersonales.controller;

import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.service.user.UserService;
import com.apirest.finanzaspersonales.service.user.UserServiceQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserServiceQuery userServiceQuery;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("GET /api/v1/users - Obtener todos los usuarios")
    void testGetAllUsers() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        given(userService.getAllUsers()).willReturn(List.of(user));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("John Doe"));
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} - Obtener un usuario por ID")
    void testGetUserById() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        given(userService.getUserById(1)).willReturn(Optional.of(user));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John Doe"));
    }

    @Test
    @DisplayName("POST /api/v1/users/register - Registrar un usuario")
    void testRegisterUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        given(userService.registerUser(any(User.class))).willReturn(user);

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John Doe"));
    }

}