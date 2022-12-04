package ru.agr.backend.looksliketests.config.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
   public UserNotFoundException(String message) {
      super(message);
   }
}
