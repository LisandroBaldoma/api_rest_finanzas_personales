package com.apirest.finanzaspersonales.repository.user.imple;

import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.repository.user.UserDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoJsonImpl implements UserDao {

    private final String FILE_PATH = "src/main/resources/users.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<User> users = new ArrayList<>();
    private static int idCounter = 1;

    public UserDaoJsonImpl() {
        loadUsersFromFile();
        if (!users.isEmpty()) {
            idCounter = users.stream().mapToInt(User::getId).max().orElse(0) + 1;
        }
    }

    @Override
    public void save(User user) {
        user.setId(idCounter++);
        users.add(user);
        saveUsersToFile();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public Optional<User> findById(int id) {
        return users.stream().filter(e -> e.getId() == id).findFirst();
    }

    @Override
    public void update(User user) {
        findById(user.getId()).ifPresent(existing -> {
            users.remove(existing);
            users.add(user);
            saveUsersToFile();
        });
    }

    @Override
    public boolean delete(int id) {
        boolean removed = users.removeIf(user -> user.getId() == id);
        if (removed) {
            saveUsersToFile();
        }
        return removed;
    }

    @Override
    public User findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    private void loadUsersFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists() && file.length() > 0) { // Verifica si el archivo tiene contenido
            try {
                users = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            } catch (IOException e) {
                System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            }
        } else {
            users = new ArrayList<>(); // Inicializa la lista vacía si el archivo está vacío o no existe
        }
    }


    private void saveUsersToFile() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
        }
    }
}
