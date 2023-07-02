package ru.agr.backend.looksliketests.controller.assignation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.assignation.dto.CreateStudentToTeacherAssignation;
import ru.agr.backend.looksliketests.controller.assignation.dto.CreateStudentToTestAssignation;
import ru.agr.backend.looksliketests.controller.assignation.mapper.StudentToTeacherResourceMapper;
import ru.agr.backend.looksliketests.controller.assignation.mapper.StudentToTestResourceMapper;
import ru.agr.backend.looksliketests.controller.resources.StudentToTeacherAssignationResource;
import ru.agr.backend.looksliketests.controller.resources.StudentToTestAssignationResource;
import ru.agr.backend.looksliketests.service.AssignationService;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ApiVersion.API_V1 + "/assignations")
public class AssignationController {
    private final AssignationService assignationService;
    private final StudentToTeacherResourceMapper studentToTeacherResourceMapper;
    private final StudentToTestResourceMapper studentToTestResourceMapper;

    @PostMapping("/student-to-teacher")
    public ResponseEntity<StudentToTeacherAssignationResource> createStudentToTeacherAssign(
            @RequestBody @Valid CreateStudentToTeacherAssignation createStudentToTeacherAssignation) {
        var assignationToSave = studentToTeacherResourceMapper.toEntity(createStudentToTeacherAssignation);
        var savedAssignation = assignationService.save(assignationToSave);
        return ResponseEntity.ok(studentToTeacherResourceMapper.toResource(savedAssignation));
    }

    @PostMapping("/student-to-test")
    public ResponseEntity<StudentToTestAssignationResource> createStudentToTestAssign(
            @RequestBody @Valid CreateStudentToTestAssignation createStudentToTestAssignation) {
        var assignationToSave = studentToTestResourceMapper.toEntity(createStudentToTestAssignation);
        var savedAssignation = assignationService.save(assignationToSave);
        return ResponseEntity.ok(studentToTestResourceMapper.toResource(savedAssignation));
    }
}
