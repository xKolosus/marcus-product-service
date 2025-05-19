package com.marcus.auth.application.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

  public UserAlreadyRegisteredException(String email) {
    super(String.format("Email: %s already registered.", email));
  }
}
