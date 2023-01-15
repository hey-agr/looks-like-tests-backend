package ru.agr.backend.looksliketests.config.security.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
   private static final String DEFAULT_ERROR_MESSAGE = "User not found";
   private static final String ERROR_MESSAGE_WITH_ID = "User with id=%s is not found";

   @Getter
   private final Long userId;

   public UserNotFoundException() {
      super(DEFAULT_ERROR_MESSAGE);
      this.userId = null;
   }

   public UserNotFoundException(Long userId) {
      super(String.format(ERROR_MESSAGE_WITH_ID, userId));
      this.userId = userId;
   }
}
