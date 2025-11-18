package com.jawad.store.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handle errors of validation , returning message of no validation as (json=map)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErors(
            MethodArgumentNotValidException exception){
        var errors =new HashMap<String,String>();
        exception.getBindingResult().getAllErrors().forEach((error)->{
            errors.put(error.getDefaultMessage(),error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);  //400 code status

    }
}
