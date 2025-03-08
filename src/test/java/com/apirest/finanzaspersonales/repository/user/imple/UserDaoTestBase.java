package com.apirest.finanzaspersonales.repository.user.imple;

import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.User.InvalidUserException;
import com.apirest.finanzaspersonales.repository.user.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public abstract class UserDaoTestBase {

    protected UserDao userDao;

    @BeforeEach
    abstract void setUp();

    @Test
    @DisplayName("Guardar un usuario asigna un ID único y lo agrega a la lista")
    void testSaveUser() {
        User user = new User();
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        userDao.save(user);

        assertEquals(1, user.getId());
        assertEquals(1, userDao.findAll().size());
    }

    @Test
    @DisplayName("Buscar todos los usuarios devuelve una lista con todos los usuarios guardados")
    void testFindAllUsers() {
        User user1 = new User();
        user1.setUserName("John Doe");
        user1.setEmail("john.doe@example.com");

        User user2 = new User();
        user2.setUserName("Jane Doe");
        user2.setEmail("jane.doe@example.com");

        userDao.save(user1);
        userDao.save(user2);

        List<User> users = userDao.findAll();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    @DisplayName("Buscar un usuario por ID devuelve el usuario correcto")
    void testFindUserById() {
        User user = new User();
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        userDao.save(user);

        Optional<User> foundUser = userDao.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getId(), foundUser.get().getId());
    }

    @Test
    @DisplayName("Actualizar un usuario modifica sus datos correctamente")
    void testUpdateUser() {
        User user = new User();
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        userDao.save(user);

        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setUserName("John Updated");
        updatedUser.setEmail("john.updated@example.com");

        userDao.update(updatedUser);

        Optional<User> foundUser = userDao.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("John Updated", foundUser.get().getUsername());
        assertEquals("john.updated@example.com", foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Eliminar un usuario lo remueve de la lista")
    void testDeleteUser() {
        User user = new User();
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        userDao.save(user);

        boolean isDeleted = userDao.delete(user.getId());

        assertTrue(isDeleted);
        assertEquals(0, userDao.findAll().size());
    }

    @Test
    @DisplayName("Buscar un usuario por email devuelve el usuario correcto")
    void testFindUserByEmail() {
        User user = new User();
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        userDao.save(user);

        User foundUser = userDao.findByEmail("john.doe@example.com");

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    @DisplayName("Buscar un usuario por email no existente devuelve null")
    void testFindUserByEmailNotFound() {
        User foundUser = userDao.findByEmail("nonexistent@example.com");

        assertNull(foundUser);
    }

    @Test
    @DisplayName("Guardar un usuario con un ID predefinido ignora el ID y asigna uno único")
    void testSaveUserWithPredefinedId() {
        User user = new User();
        user.setId(999); // ID predefinido
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        userDao.save(user);

        assertNotEquals(999, user.getId());
        assertEquals(1, user.getId());
    }

    @Test
    @DisplayName("Actualizar un usuario no existente no modifica la lista")
    void testUpdateNonExistentUser() {
        User user = new User();
        user.setId(999); // ID que no existe
        user.setUserName("Non Existent");
        user.setEmail("nonexistent@example.com");

        userDao.update(user);

        assertFalse(userDao.findById(999).isPresent());
    }

    @Test
    @DisplayName("Eliminar un usuario no existente devuelve false")
    void testDeleteNonExistentUser() {
        boolean isDeleted = userDao.delete(999); // ID que no existe

        assertFalse(isDeleted);
        assertEquals(0, userDao.findAll().size());
    }

    @Test
    @DisplayName("Buscar un usuario por ID no existente devuelve un Optional vacío")
    void testFindByIdNonExistentUser() {
        Optional<User> foundUser = userDao.findById(999); // ID que no existe

        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("Guardar un usuario con email duplicado permite múltiples usuarios con el mismo email")
    void testSaveUserWithDuplicateEmail() {
        User user1 = new User();
        user1.setUserName("John Doe");
        user1.setEmail("john.doe@example.com");

        User user2 = new User();
        user2.setUserName("Jane Doe");
        user2.setEmail("john.doe@example.com"); // Mismo email que user1

        userDao.save(user1);
        userDao.save(user2);

        assertEquals(2, userDao.findAll().size());
    }

    @Test
    @DisplayName("Buscar un usuario por email es insensible a mayúsculas y minúsculas")
    void testFindByEmailCaseInsensitive() {
        User user = new User();
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");

        userDao.save(user);

        User foundUser = userDao.findByEmail("JOHN.DOE@EXAMPLE.COM"); // Email en mayúsculas

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    @DisplayName("Guardar un usuario nulo lanza una excepción")
    void testSaveNullUser() {
        assertThrows(InvalidUserException.class, () -> {
            userDao.save(null);
        });
    }

    @Test
    @DisplayName("Actualizar un usuario nulo lanza una excepción")
    void testUpdateNullUser() {
        assertThrows(InvalidUserException.class, () -> {
            userDao.update(null);
        });
    }

    @Test
    @DisplayName("Eliminar un usuario con ID negativo no tiene efecto")
    void testDeleteUserWithNegativeId() {
        boolean isDeleted = userDao.delete(-1); // ID negativo

        assertFalse(isDeleted);
    }

    @Test
    @DisplayName("El contador de IDs asigna secuencias correctamente al guardar múltiples usuarios")
    void testIdCounterSequence() {
        User user1 = new User();
        user1.setUserName("User 1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setUserName("User 2");
        user2.setEmail("user2@example.com");

        userDao.save(user1);
        userDao.save(user2);

        assertEquals(1, user1.getId());
        assertEquals(2, user2.getId());
    }

    @Test
    @DisplayName("Buscar un usuario por email nulo devuelve null")
    void testFindByEmailNull() {
        User foundUser = userDao.findByEmail(null);

        assertNull(foundUser);
    }

    @Test
    @DisplayName("Operaciones en una lista vacía no tienen efecto")
    void testOperationsOnEmptyList() {
        assertTrue(userDao.findAll().isEmpty());
        assertFalse(userDao.findById(1).isPresent());
        assertFalse(userDao.delete(1));
    }
}
