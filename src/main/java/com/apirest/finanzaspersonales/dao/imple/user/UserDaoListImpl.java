package com.apirest.finanzaspersonales.dao.imple.user;

import com.apirest.finanzaspersonales.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoListImpl implements UserDao {

    private final List<User> users = new ArrayList<>();

    @Override
    public int save(User user) {
        users.add(user);
        return user.getId();
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
    public boolean update(User user) {
        return findById(user.getId()).map(existing -> {
            users.remove(existing);
            users.add(user);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean delete(int id) {
        return users.removeIf(user -> user.getId() == id);
    }
}
