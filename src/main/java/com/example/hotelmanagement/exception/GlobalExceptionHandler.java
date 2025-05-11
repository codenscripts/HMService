package com.example.hotelmanagement.exception;

import com.example.hotelmanagement.exception.error.ErrorDetail;
import com.example.hotelmanagement.exception.error.ResourceNotFoundException;
import com.example.hotelmanagement.exception.error.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorDetail(
                        fieldError.getField(),
                        fieldError.getDefaultMessage(),
                        fieldError.getRejectedValue()
                )).collect(Collectors.toList());

        return new ValidationErrorResponse(
                "Validation Failed",
                errors,
                request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value()); // 404
        body.put("error", "Not Found");
        body.put("message", ex.getMessage()); // Get message from your exception
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    // You can add more @ExceptionHandler methods here to handle other specific
    // exceptions globally (e.g., custom exceptions for RoomNotFound, etc.)
    // For example, handling the ResponseStatusException we used for CONFLICT:
        /*
        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException ex, WebRequest request) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", ex.getStatusCode().value());
            body.put("error", ex.getStatusCode().getReasonPhrase());
            body.put("message", ex.getReason());
            body.put("path", ((ServletWebRequest)request).getRequest().getRequestURI());

            return new ResponseEntity<>(body, ex.getStatusCode());
        }
        */
    // Note: Handling ResponseStatusException might conflict slightly if you want *only*
    // validation errors handled this way. Adjust as needed for desired behavior.

}
