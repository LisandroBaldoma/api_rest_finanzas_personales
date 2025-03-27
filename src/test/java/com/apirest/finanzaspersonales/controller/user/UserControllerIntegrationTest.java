package com.apirest.finanzaspersonales.controller.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.service.user.UserServiceQuery;
import com.apirest.finanzaspersonales.service.user.imple.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;  // Simula peticiones HTTP

    @Autowired
    private ObjectMapper objectMapper;  // Para convertir objetos a JSON

    @MockitoBean
    private UserServiceImpl userService;  // Mock del servicio principal

    @MockitoBean
    private UserServiceQuery userServiceQuery;  // Mock del servicio de consultas

    private UserRequest request1;
    private UserResponse response1;

    @BeforeEach
    void setUp(){
        request1 = new UserRequest("Lisandro", "lbalodma@jubbler.com.ar", "password");
        response1 = new UserResponse("Lisandro", "lbalodma@jubbler.com.ar");
    }

    // --- Tests para cada endpoint ---

    @Test
    @DisplayName("GET /api/v1/users - Debe retornar 200 y lista de usuarios")
    void getAllUsers_ShouldReturnUsers() throws Exception {
        // Datos de prueba
        UserResponse user1 = new UserResponse(1L, "User 1", "user1@example.com");
        UserResponse user2 = new UserResponse(2L, "User 2", "user2@example.com");
        List<UserResponse> users = List.of(user1, user2);

        // Mock del servicio
        when(userService.getAllUsers()).thenReturn(users);

        // Ejecutar petición GET y verificar respuesta
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"));
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} - Debe retornar 200 si el usuario existe")
    void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {
        Long userId = 1L;
        UserResponse user = new UserResponse( "Test User", "test@example.com");

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} - Debe retornar 404 si el usuario no existe")
    void getUserById_WhenUserNotExists_ShouldReturn404() throws Exception {
        Long userId = 99L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/users/register - Debe retornar 201 y el usuario creado")
    void registerUser_ShouldReturnCreatedUser() throws Exception {

        when(userService.registerUser(any())).thenReturn(response1);

        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Lisandro"))
                .andExpect(jsonPath("$.email").value("lbalodma@jubbler.com.ar"));

        verify(userService, times(1)).registerUser(any());
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} - Debe retornar 200 y el usuario actualizado")
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        // Arrange: Datos de prueba
        Long userId = 1L;
        UserRequest request = new UserRequest("Updated User2", "updated@example.com", "password");
        UserResponse response = new UserResponse(1L, "Updated User2", "updated@example.com");

        // Configuramos el mock del servicio
        when(userService.updateUser(eq(userId), any(UserRequest.class))).thenReturn(response);

        // Act & Assert: Ejecutamos la solicitud y validamos la respuesta
        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User2"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));

        // Verify: Aseguramos que el servicio fue llamado correctamente
        verify(userService, times(1)).updateUser(eq(userId), any(UserRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} - Debe retornar 204")
    void deleteUser_ShouldReturnNoContent() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/api/v1/users/{id}", userId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).removeUser(userId);  // Verifica que se llamó al servicio
    }
}