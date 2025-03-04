package com.apirest.finanzaspersonales.config;

import com.apirest.finanzaspersonales.repository.user.UserDao;
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

    public UserDaoConfig(UserDaoListImpl userDaoListImpl, UserDaoJsonImpl userDaoJsonImpl) {
        this.userDaoListImpl = userDaoListImpl;
        this.userDaoJsonImpl = userDaoJsonImpl;
    }

    @Bean
    public UserDao userDao() {
        if ("json".equalsIgnoreCase(daoImpl)) {
            System.out.println("🔌 Cargando implementación JSON...");
            return userDaoJsonImpl;
        }
        System.out.println("🔌 Cargando implementación LIST...");
        return userDaoListImpl;
    }
}
