package com.apirest.finanzaspersonales.controller;

import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.controller.user.UserController;
import com.apirest.finanzaspersonales.service.user.UserService;
import com.apirest.finanzaspersonales.service.user.UserServiceQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserServiceQuery userServiceQuery;

    private UserResponse userResponse;
    private UserRequest userRequest;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userResponse = new UserResponse();
        userResponse.setUsername("John Doe");
        userResponse.setEmail("john.doe@example.com");

        userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPassword("password123");
    }

    @Test
    @DisplayName("GET /api/v1/users - Obtener todos los usuarios")
    void testGetAllUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(List.of(userResponse));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].username").value("John Doe"));
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} - Obtener un usuario por ID")
    void testGetUserById() throws Exception {

    }

    @Test
    @DisplayName("GET /api/v1/users/{id} - Usuario no encontrado")
    void testGetUserById_NotFound() throws Exception {

    }

    @Test
    @DisplayName("POST /api/v1/users/register - Registrar un usuario")
    void testRegisterUser() throws Exception {
        given(userService.registerUser(any(UserRequest.class))).willReturn(userResponse);

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("John Doe"));
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} - Actualizar usuario")
    void testUpdateUser() throws Exception {

    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} - Eliminar usuario")
    void testDeleteUser() throws Exception {

    }

    @Test
    @DisplayName("GET /api/v1/users/by-email - Buscar usuario por email")
    void testGetUserByEmail() throws Exception {
        given(userServiceQuery.getUserByEmail("john.doe@example.com")).willReturn(userResponse);

        mockMvc.perform(get("/api/v1/users/by-email")
                        .param("email", "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John Doe"));
    }

    @Test
    @DisplayName("GET /api/v1/users/email-exists - Verificar si email existe")
    void testEmailExists() throws Exception {
        given(userServiceQuery.emailExists("john.doe@example.com")).willReturn(true);

        mockMvc.perform(get("/api/v1/users/email-exists")
                        .param("email", "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("GET /api/v1/users/count - Contar usuarios")
    void testCountUsers() throws Exception {
        given(userServiceQuery.countUsers()).willReturn(10L);

        mockMvc.perform(get("/api/v1/users/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    @DisplayName("GET /api/v1/users/by-name - Buscar usuarios por nombre")
    void testFindByName() throws Exception {
        given(userServiceQuery.findByName("John")).willReturn(List.of(userResponse));

        mockMvc.perform(get("/api/v1/users/by-name")
                        .param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].username").value("John Doe"));
    }

}