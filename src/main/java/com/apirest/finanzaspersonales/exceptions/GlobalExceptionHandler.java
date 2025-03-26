package com.apirest.finanzaspersonales.exceptions;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(AlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse("Conflicto: El recurso ya existe", Collections.singletonList(e.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Recurso no encontrado", Collections.singletonList(e.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(BadRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse("Solicitud incorrecta", Collections.singletonList(e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse("Error interno del servidor", Collections.singletonList(e.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Maneja excepciones de validación (si tienes validaciones de @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("Error de validación", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidExpenseException.class)
    public ResponseEntity<ErrorResponse> handleInvalidExpenseException(InvalidExpenseException e) {
        // Creamos el ErrorResponse con el mensaje de la excepción y un título apropiado
        ErrorResponse errorResponse = new ErrorResponse(
                "Datos inválidos en la solicitud",
                Collections.singletonList(e.getMessage())  // Aquí se pasa el mensaje que incluye la información detallada
        );

        // Retornamos un 400 Bad Request con el mensaje
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
