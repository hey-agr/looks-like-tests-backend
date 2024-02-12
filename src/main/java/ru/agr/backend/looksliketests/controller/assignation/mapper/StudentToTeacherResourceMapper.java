package ru.agr.backend.looksliketests.controller.assignation.mapper;

/**
 * @author Arslan Rabadanov
 */

import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.agr.backend.looksliketests.controller.assignation.dto.CreateStudentToTeacherAssignation;
import ru.agr.backend.looksliketests.controller.resources.StudentToTeacherAssignationResource;
import ru.agr.backend.looksliketests.db.entity.main.StudentToTeacherAssignation;

@Mapper(componentModel = "spring")
public interface StudentToTeacherResourceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    StudentToTeacherAssignation toEntity(@NonNull CreateStudentToTeacherAssignation createStudentToTeacherAssignation);
    StudentToTeacherAssignationResource toResource(@NonNull StudentToTeacherAssignation studentToTeacherAssignation);
}
