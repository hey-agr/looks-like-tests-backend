package ru.agr.backend.looksliketests.db.entity.main;

/**
 * @author Arslan Rabadanov
 */
public enum QuestionType {
    OPTIONS,
    OPTIONS_MULTIPLY,
    WRITING;

    public boolean isOption() {
        return this == OPTIONS || this == OPTIONS_MULTIPLY;
    }
}
