package com.apirest.finanzaspersonales.controller;

import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.service.user.UserService;
import com.apirest.finanzaspersonales.repository.user.imple.UserDaoListImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

    @RestController

    @RequestMapping("api/v1/users")

    public class UserController {
        @Autowired
        private final UserService userService;

        public UserController(UserDaoListImpl userDaoImpl, UserService userService) {
            this.userService = userService;

        }

        // Obtener todos los usuarios
        @GetMapping
        public List<User> getAllUsers() {
            return userService.getAllUsers();
        }

        // Obtener un usuario por ID
        @GetMapping("/{id}")
        public ResponseEntity<Optional<User>> getUserById(@PathVariable int id) {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        // Registrar usuario
        @PostMapping("/register")
        public ResponseEntity<User> registerUser(@RequestBody User user) {
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        }

        // Actualizar usuario
        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
            user.setId(id);
            Optional<User> updatedUser = userService.updateUser(user);
            return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteUser(@PathVariable int id) {
            userService.removeUser(id);
            return ResponseEntity.ok("Usuario eliminado correctamente.");

        }

        @GetMapping("/test")
        public String testEndpoint() {
            return "El endpoint está funcionando correctamente.";
        }
    }
