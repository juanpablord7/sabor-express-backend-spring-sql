package com.miempresa.serviceuser.utils.exceptions.exceptionsHandler;

import com.miempresa.serviceuser.utils.exceptions.customExceptions.UnsafeInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo para errores de validación de @Valid
    @ExceptionHandler
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

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

    // Manejo de errores personalizados tipo IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArg(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                Map.of(
                        "error", ex.getMessage()
                ),
                HttpStatus.BAD_REQUEST);
    }

    // Manejo para errores de parámetros inválidos en la URL
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(
                Map.of(
                        "error", "Tipo de argumento inválido"
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidFormat(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                Map.of(
                        "error", "Datos mal formateados: revisa los tipos de los campos."
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    // Manejo para cualquier otro error no controlado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneral(Exception ex) {
        log.error("Error inesperado en la app", ex);
        return new ResponseEntity<>(
                Map.of(
                        "error", "Ocurrió un error inesperado"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //Custom Exceptions:
    @ExceptionHandler(UnsafeInputException.class)
    public ResponseEntity<Map<String, String>> handleUnsafeInput(UnsafeInputException ex) {
        // Retornar la respuesta directamente en un Map, con el mensaje de error y el código HTTP
        return new ResponseEntity<>(
                Map.of(
                        "error", ex.getMessage()
                ),
                HttpStatus.BAD_REQUEST);
    }

}
