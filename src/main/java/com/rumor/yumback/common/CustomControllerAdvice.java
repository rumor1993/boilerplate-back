package com.rumor.yumback.common;

import com.rumor.yumback.common.errors.StorageException;
import com.rumor.yumback.common.errors.StorageFileNotFoundException;
import com.rumor.yumback.common.errors.UserNotFoundException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorResponse> handleStorageException(StorageException ex) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStorageFileNotFoundException(StorageFileNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(UnexpectedTypeException ex) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(UserNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }
}
