package com.wa.condominio.resources.exceptions;

import com.wa.condominio.services.exceptions.DatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseException(DatabaseException ex) {
        Map<String, Object> errorDetails = new LinkedHashMap<>();
        errorDetails.put("type", "https://tools.ietf.org/html/rfc7231#section-6.5.1");
        errorDetails.put("title", "One or more validation errors occurred.");
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("traceId", "your-trace-id-here");
        errorDetails.put("errors", Collections.singletonMap("global", Collections.singletonList(ex.getMessage())));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorDetails = new LinkedHashMap<>();
        errorDetails.put("type", "https://tools.ietf.org/html/rfc7231#section-6.6.1");
        errorDetails.put("title", "Internal Server Error");
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("traceId", "your-trace-id-here");
        errorDetails.put("errors", Collections.singletonMap("global", Collections.singletonList("Erro interno do servidor.")));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}