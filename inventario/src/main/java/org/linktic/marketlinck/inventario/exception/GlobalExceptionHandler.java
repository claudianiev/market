package org.linktic.marketlinck.inventario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manejo de errores para recursos no encontrados
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = Map.of(
                "errors", List.of(Map.of(
                        "status", "404",
                        "title", ex.getMessage()
                ))
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
