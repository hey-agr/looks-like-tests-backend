package ru.agr.backend.looksliketests.controller.auth.exception;


/**
 * @author Arslan Rabadanov
 */
public class DuplicateEmailException extends DuplicationException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
