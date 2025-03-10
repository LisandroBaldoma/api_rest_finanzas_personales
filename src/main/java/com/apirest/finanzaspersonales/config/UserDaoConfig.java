package com.apirest.finanzaspersonales.config;

import com.apirest.finanzaspersonales.repository.user.UserDao;
import com.apirest.finanzaspersonales.repository.user.imple.UserDaoH2Impl;
import com.apirest.finanzaspersonales.repository.user.imple.UserDaoJsonImpl;
import com.apirest.finanzaspersonales.repository.user.imple.UserDaoListImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoConfig {
    @Value("${app.userdao.impl}")
    private String daoImpl;

    private final UserDaoListImpl userDaoListImpl;
    private final UserDaoJsonImpl userDaoJsonImpl;
    private final UserDaoH2Impl userDaoH2Impl;

    public UserDaoConfig(UserDaoListImpl userDaoListImpl, UserDaoJsonImpl userDaoJsonImpl, UserDaoH2Impl userDaoH2Impl) {
        this.userDaoListImpl = userDaoListImpl;
        this.userDaoJsonImpl = userDaoJsonImpl;
        this.userDaoH2Impl = userDaoH2Impl;
    }

    @Bean
    public UserDao userDao() {
        if ("json".equalsIgnoreCase(daoImpl)) {
            System.out.println("🔌 Cargando implementación JSON...");
            return userDaoJsonImpl;
        }else if ("h2".equalsIgnoreCase(daoImpl)){
            System.out.println("🔌 Cargando implementación H2...");
            return userDaoH2Impl;
        }
        System.out.println("🔌 Cargando implementación LIST...");
        return userDaoListImpl;
    }
}
