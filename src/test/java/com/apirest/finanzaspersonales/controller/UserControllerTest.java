package com.apirest.finanzaspersonales.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.apirest.finanzaspersonales.controller.user.UserController;
import com.apirest.finanzaspersonales.controller.user.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.user.model.response.UserResponse;
import com.apirest.finanzaspersonales.service.user.UserServiceQuery;
import com.apirest.finanzaspersonales.service.user.imple.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserServiceQuery userServiceQuery;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(userController).build();
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() throws Exception {

    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {

    }

    @Test
    void getUserById_WhenUserNotExists_ShouldReturnNotFound() throws Exception {

    }

    @Test
    void registerUser_ShouldReturnCreatedStatusAndUser() throws Exception {

    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {

    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {

    }

    @Test
    void getUserByEmail_ShouldReturnUser() throws Exception {

    }

    @Test
    void emailExists_WhenEmailExists_ShouldReturnTrue() throws Exception {

    }

    @Test
    void emailExists_WhenEmailNotExists_ShouldReturnFalse() throws Exception {

    }

    @Test
    void countUsers_ShouldReturnUserCount() throws Exception {

    }

    @Test
    void findByName_ShouldReturnMatchingUsers() throws Exception {

    }

    @Test
    void findByName_WhenNoMatches_ShouldReturnEmptyList() throws Exception {

    }
}