package ru.agr.backend.looksliketests.controller.auth.exception;


/**
 * @author Arslan Rabadanov
 */
public class DuplicateUsernameException extends DuplicationException {

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
