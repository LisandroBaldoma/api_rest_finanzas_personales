package com.apirest.finanzaspersonales.entity;

public class User {
    private int id;
    private final String username;
    private String email;
    private String password;


    public User(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String hashedPassword) {
        this.password = hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
