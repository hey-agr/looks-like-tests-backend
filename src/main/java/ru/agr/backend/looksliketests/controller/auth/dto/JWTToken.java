package ru.agr.backend.looksliketests.controller.auth.dto;

import lombok.NonNull;

/**
 * @author Arslan Rabadanov
 */
public record JWTToken(@NonNull String value) {}
