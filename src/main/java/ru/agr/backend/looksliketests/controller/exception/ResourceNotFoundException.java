package ru.agr.backend.looksliketests.controller.exception;

/**
 * @author Arslan Rabadanov
 */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
