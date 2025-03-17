package com.apirest.finanzaspersonales.controller.model.response;


public class UserResponse {
    private Integer id;
    private String username;
    private String email;


    public UserResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
