package com.apirest.finanzaspersonales.DataBase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test") // Usa el perfil de prueba con H2 en memoria
public class DatabaseConnectionTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("Should successfully connect to the database and execute a simple query")
    void shouldConnectToDatabase() {
        // Ejecuta una consulta para verificar la conexión
        int result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);

        // Verifica que la consulta se ejecutó correctamente
        assertEquals(1, result, "The database connection should be active and return 1");
    }
    @Test
    @DisplayName("Should use file mode for H2 database")
    void shouldUseFileModeForH2Database() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            System.out.println("🔍 Database URL: " + url);
            assertTrue(url.contains("jdbc:h2:file"), "Expected database to be in file mode");
        }
    }
}
