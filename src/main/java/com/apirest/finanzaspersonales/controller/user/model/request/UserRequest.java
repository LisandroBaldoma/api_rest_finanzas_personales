package com.apirest.finanzaspersonales.controller.user.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRequest {
    private Long id;
    private String name;
    private String email;
    private String password; // 🚨 Requerido en el registro, pero opcional en update

}
