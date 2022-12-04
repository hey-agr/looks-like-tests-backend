package ru.agr.backend.looksliketests.config.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {
   public UserNotActivatedException(String message) {
      super(message);
   }
}
