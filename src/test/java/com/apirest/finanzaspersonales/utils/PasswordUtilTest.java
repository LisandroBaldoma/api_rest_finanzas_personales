package com.apirest.finanzaspersonales.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    @DisplayName("Should hash the password correctly using SHA-256")
    void hashPassword_shouldReturnHashedString_whenValidPasswordProvided() {
        // Given
        String password = "mySecurePassword";

        // When
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Then
        assertNotNull(hashedPassword);
        assertEquals(64, hashedPassword.length()); // SHA-256 siempre genera una cadena de 64 caracteres
    }

}