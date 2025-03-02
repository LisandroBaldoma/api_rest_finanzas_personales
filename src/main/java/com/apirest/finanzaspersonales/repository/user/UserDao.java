package com.apirest.finanzaspersonales.repository.user;

import com.apirest.finanzaspersonales.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao {
    void save(User user);  // Guarda un usuario y devuelve el ID generado.

    List<User> findAll();  // Obtiene todos los usuarios.

    Optional<User> findById(int id);  // Busca un usuario por su ID.

    void update(User user);  // Actualiza un usuario existente.

    boolean delete(int id);  // Elimina un usuario por su ID.

    User findByEmail(String email);


}
