package ru.agr.backend.looksliketests.controller.resources;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Arslan Rabadanov
 */
@Jacksonized
@SuperBuilder
@Data
public class PageableResource {
    Integer pageNumber;
    Integer pageSize;
    Integer size;
    Long totalSize;
    Integer totalPages;
}
