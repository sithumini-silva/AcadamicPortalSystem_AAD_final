package org.example.academic_portal_system.adviser;

import org.example.academic_portal_system.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseUtil> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(
                new ResponseUtil(400, "Validation Failed", errors),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public ResponseEntity<?> handleFileSizeLimitExceeded(MultipartException ex) {
        return ResponseEntity
                .status(413) // HTTP Status for Payload Too Large
                .body(Map.of("message", "File size exceeds the allowed limit"));
    }

}
