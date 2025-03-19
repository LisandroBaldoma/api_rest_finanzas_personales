package com.apirest.finanzaspersonales.controller;

import com.apirest.finanzaspersonales.controller.model.request.UserRequest;
import com.apirest.finanzaspersonales.controller.model.response.UserResponse;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.service.user.UserService;
import com.apirest.finanzaspersonales.service.user.UserServiceQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

    @Slf4j
    @RestController
    @RequestMapping("api/v1/users")

    public class UserController {
        private static final Logger log = LogManager.getLogger(UserController.class);
        @Autowired
        private final UserService userService;
        private final UserServiceQuery userServiceQuery;

        public UserController(UserService userService, UserServiceQuery userServiceQuery) {
            this.userService = userService;
            this.userServiceQuery = userServiceQuery;
        }


        // Obtener todos los usuarios
        @GetMapping
        public List<UserResponse> getAllUsers() {
            return userService.getAllUsers();
        }

        // Obtener un usuario por ID
        @GetMapping("/{id}")
        public ResponseEntity<Optional<UserResponse>> getUserById(@PathVariable int id) {
            Optional<UserResponse> user = userService.getUserById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        // Registrar usuario
        @PostMapping("/register")
        public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
            log.info("Post register /register ");
            UserResponse newUser = userService.registerUser(userRequest);
            // Retorna un 201 Created con la ubicación del nuevo recurso
            return ResponseEntity
                    .status(HttpStatus.CREATED) // Código 201
                    .body(newUser);
        }

        // Actualizar usuario
        @PutMapping("/{id}")
        public ResponseEntity<UserResponse> updateUser(@PathVariable int id, @RequestBody UserRequest userRequest) {
            userRequest.setId(id);
            Optional<UserResponse> updatedUser = userService.updateUser(userRequest);
            return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteUser(@PathVariable int id) {
            userService.removeUser(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content

        }
        // Obtener usuario por email (RequestParam)
        @GetMapping("/by-email")
        public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
            UserResponse user = userServiceQuery.getUserByEmail(email);
            return ResponseEntity.ok(user);
        }

        // Verificar si el email existe
        @GetMapping("/email-exists")
        public ResponseEntity<Boolean> emailExists(@RequestParam String email) {
            return ResponseEntity.ok(userServiceQuery.emailExists(email));
        }

        // Contar usuarios
        @GetMapping("/count")
        public ResponseEntity<Long> countUsers() {
            return ResponseEntity.ok(userServiceQuery.countUsers());
        }

        // Buscar usuarios por nombre
        @GetMapping("/by-name")
        public ResponseEntity<List<UserResponse>> findByName(@RequestParam String name) {
            return ResponseEntity.ok(userServiceQuery.findByName(name));
        }

    }
