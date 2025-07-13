package com.miempresa.serviceorder.utils.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handlerIllegalAccess(AccessDeniedException ex){
        String message = ex.getMessage();
        if (message == null || message.isBlank()) {
            message = "You don't have the credentials for this operation.";
        }
        return new ResponseEntity<>(
                Map.of("error",  message),
                HttpStatus.BAD_REQUEST);
    }
}
