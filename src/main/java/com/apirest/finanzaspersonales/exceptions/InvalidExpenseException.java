package com.apirest.finanzaspersonales.exceptions;

public class InvalidExpenseException extends RuntimeException{
    public InvalidExpenseException(String message) {
        super(message);
    }

}
