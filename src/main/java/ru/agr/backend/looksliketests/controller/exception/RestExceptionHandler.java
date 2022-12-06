package ru.agr.backend.looksliketests.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.agr.backend.looksliketests.controller.auth.exception.DuplicateEmailException;
import ru.agr.backend.looksliketests.controller.auth.exception.DuplicateUsernameException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFoundEntity(EntityNotFoundException e, ServerHttpRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ErrorMessage> authenticationException(AuthenticationException e, ServerHttpRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessage> httpMessageNotReadableException(HttpMessageNotReadableException e, ServerHttpRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException e, ServerHttpRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DuplicateEmailException.class})
    public ResponseEntity<ErrorMessage> duplicateEmailException(DuplicateEmailException e, ServerHttpRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DuplicateUsernameException.class})
    public ResponseEntity<ErrorMessage> duplicateUsernameException(DuplicateUsernameException e, ServerHttpRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
//    public ResponseEntity<ErrorMessage> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, ServerHttpRequest request) {
//        return getErrorMessageResponseEntity(e, request, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> otherException(Exception e, ServerHttpRequest request) {
        return getErrorMessageResponseEntity(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(Exception e, ServerHttpRequest request, HttpStatus httpStatus) {
        ErrorMessage errorMessage = getErrorMessage(e, request, httpStatus);
        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    private ErrorMessage getErrorMessage(Exception e, ServerHttpRequest request, HttpStatus httpStatus) {
        return ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .message(isNull(e.getMessage()) ? e.getClass().getName() : e.getMessage())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .path(request.getURI().toString())
                .build();
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class ErrorMessage {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
    }
}
