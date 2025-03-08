package com.apirest.finanzaspersonales.exceptions.User;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message) {
        super(message);
    }

}
