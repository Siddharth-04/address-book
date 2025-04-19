package com.bridgelabz.addressbook.exceptions;

import com.bridgelabz.addressbook.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    final String message = "Error while using Rest Api";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseDTO handleHttpMethodNotReadableException(HttpMessageNotReadableException exception){
        log.error("Invalid date formate",exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseDTO("Date format invalid, should be dd MMM yyyy",exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String,String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errors.put(field,message);
            log.error("Field validation failed for : "+field,HttpStatus.BAD_REQUEST);
        });

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
