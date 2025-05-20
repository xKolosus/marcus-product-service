package com.marcus.config;

import com.marcus.auth.application.exception.UserAlreadyRegisteredException;
import com.marcus.config.exception.ResourceNotFoundException;
import com.marcus.purchase.exception.PurchaseFailedException;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  public static final String ERROR = "error";
  public static final String CAUSE = "cause";

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
    Map<String, String> error = new HashMap<>();
    error.put(ERROR, "Internal Server Error");
    error.put(CAUSE, ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put(ERROR, "Argument missing");
    response.put(CAUSE, ex.getBody().getDetail());

    List<Map<String, String>> missed = new ArrayList<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              Map<String, String> errorDetail = new HashMap<>();
              errorDetail.put(error.getField(), error.getDefaultMessage());
              missed.add(errorDetail);
            });

    response.put("missed", missed);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put(ERROR, "Argument not readable");
    response.put(CAUSE, ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, String>> handleConstraintViolationException(
      ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put(CAUSE, ex.getMessage());
    ex.getConstraintViolations()
        .forEach(
            violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Map<String, String>> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    Map<String, String> error = new HashMap<>();
    error.put(ERROR, "Resource Not Found");
    error.put(CAUSE, ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserAlreadyRegisteredException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleUserAlreadyRegisteredException(
      UserAlreadyRegisteredException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put(ERROR, "Failed registration");
    response.put(CAUSE, ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<Map<String, Object>> handleAuthenticationException(
      AuthenticationException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put(ERROR, "Failed log in");
    response.put(CAUSE, ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(PurchaseFailedException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Map<String, Object>> handlePurchaseFailedException(
      PurchaseFailedException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put(ERROR, "Purchase not done");
    response.put(CAUSE, ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }
}
