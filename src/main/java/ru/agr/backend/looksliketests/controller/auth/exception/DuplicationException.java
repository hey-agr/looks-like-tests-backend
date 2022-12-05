package ru.agr.backend.looksliketests.controller.auth.exception;

/**
 * @author Arslan Rabadanov
 */
public abstract class DuplicationException extends Exception {
    protected DuplicationException(String message) {
        super(message);
    }
}
