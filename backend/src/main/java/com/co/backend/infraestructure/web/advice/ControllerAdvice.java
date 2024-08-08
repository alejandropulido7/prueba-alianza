package com.co.backend.infraestructure.web.advice;

import com.co.backend.application.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    private ResponseEntity<ApiError> handlerNoSuchElementException(ApplicationException exception){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiError apiError = new ApiError(httpStatus.value(), exception.getMessage());
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(apiError);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> globalExceptionHandler(RuntimeException ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError apiError = new ApiError(httpStatus.value(), ex.getMessage());
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(apiError);
    }
}
