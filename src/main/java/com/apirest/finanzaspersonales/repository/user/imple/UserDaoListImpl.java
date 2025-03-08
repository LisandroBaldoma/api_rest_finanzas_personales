package com.apirest.finanzaspersonales.repository.user.imple;

import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.User.InvalidUserException;
import com.apirest.finanzaspersonales.repository.user.UserDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoListImpl implements UserDao {

    private final List<User> users = new ArrayList<>();
    private static int idCounter = 1;

    @Override
    public void save(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
        user.setId(idCounter++); // Asigna y aumenta el contador
        users.add(user);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users); // Devolvemos una copia para evitar modificaciones externas
    }

    @Override
    public Optional<User> findById(int id) {
            return users.stream().filter(e -> e.getId() == id).findFirst();

    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }

        findById(user.getId()).map(existing -> {
            users.remove(existing);
            users.add(user);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return users.removeIf(user -> user.getId() == id);
    }

    @Override
    public User findByEmail(String email) {

        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    // Método para reiniciar el contador y limpiar la lista (útil para tests)
    public void reset() {
        users.clear();
        idCounter = 1;
    }
}
