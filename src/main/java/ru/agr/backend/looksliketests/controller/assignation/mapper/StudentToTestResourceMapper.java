package ru.agr.backend.looksliketests.controller.assignation.mapper;

/**
 * @author Arslan Rabadanov
 */

import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.agr.backend.looksliketests.controller.assignation.dto.CreateStudentToTestAssignation;
import ru.agr.backend.looksliketests.controller.resources.StudentToTestAssignationResource;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTestAssignation;

@Mapper(componentModel = "spring")
public interface StudentToTestResourceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    StudentToTestAssignation toEntity(@NonNull CreateStudentToTestAssignation createStudentToTestAssignation);
    StudentToTestAssignationResource toResource(@NonNull StudentToTestAssignation studentToTestAssignation);
}
