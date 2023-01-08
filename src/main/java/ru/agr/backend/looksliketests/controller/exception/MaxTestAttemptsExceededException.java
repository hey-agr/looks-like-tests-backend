package ru.agr.backend.looksliketests.controller.exception;

/**
 * @author Arslan Rabadanov
 */
public class MaxTestAttemptsExceededException extends Exception {
    public MaxTestAttemptsExceededException(String message) {
        super(message);
    }
}
