package com.apirest.finanzaspersonales.controller.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserResponse {
    private Long id;
    private String name;
    private String email;

    public UserResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
