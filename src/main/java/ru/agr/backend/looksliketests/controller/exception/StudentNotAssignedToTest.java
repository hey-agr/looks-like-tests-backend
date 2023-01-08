package ru.agr.backend.looksliketests.controller.exception;

/**
 * @author Arslan Rabadanov
 */
public class StudentNotAssignedToTest extends Exception {
    private static final String ERROR_MESSAGE_TEMPLATE = "Student: %s is not assigned to test: %s";

    public StudentNotAssignedToTest(String username, String testDescription) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, username, testDescription));
    }
}
