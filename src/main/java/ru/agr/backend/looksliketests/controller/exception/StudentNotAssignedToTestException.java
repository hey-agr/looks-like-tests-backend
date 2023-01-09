package ru.agr.backend.looksliketests.controller.exception;

/**
 * @author Arslan Rabadanov
 */
public class StudentNotAssignedToTestException extends Exception {
    private static final String ERROR_MESSAGE_TEMPLATE = "Student: %s is not assigned to test: %s";

    public StudentNotAssignedToTestException(String username, String testDescription) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, username, testDescription));
    }
}
