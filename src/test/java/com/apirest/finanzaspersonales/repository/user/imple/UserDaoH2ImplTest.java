package com.apirest.finanzaspersonales.repository.user.imple;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
// TODO: CONFIGURAR EL TEST PARA H2 IMPLEMENTATION
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class UserDaoH2ImplTest extends UserDaoTestBase{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // Inicializa el DAO con el JdbcTemplate inyectado
        userDao = new UserDaoH2Impl(jdbcTemplate);
    }

}