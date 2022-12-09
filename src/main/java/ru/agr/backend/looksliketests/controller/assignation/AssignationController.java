package ru.agr.backend.looksliketests.controller.assignation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.backend.looksliketests.config.security.exception.UserNotFoundException;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.assignation.dto.CreateStudentToTeacherAssignation;
import ru.agr.backend.looksliketests.controller.assignation.dto.CreateStudentToTestAssignation;
import ru.agr.backend.looksliketests.controller.assignation.mapper.StudentToTeacherResourceMapper;
import ru.agr.backend.looksliketests.controller.assignation.mapper.StudentToTestResourceMapper;
import ru.agr.backend.looksliketests.controller.resources.StudentToTeacherAssignationResource;
import ru.agr.backend.looksliketests.controller.resources.StudentToTestAssignationResource;
import ru.agr.backend.looksliketests.db.entity.auth.UserAuthority;
import ru.agr.backend.looksliketests.service.AssignationService;
import ru.agr.backend.looksliketests.service.auth.UserService;

import javax.validation.Valid;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ApiVersion.API_V1 + "/assignations")
public class AssignationController {
    private static final UserAuthority.AuthorityName STUDENT_AUTHORITY = UserAuthority.AuthorityName.STUDENT;
    private static final UserAuthority.AuthorityName TEACHER_AUTHORITY = UserAuthority.AuthorityName.TEACHER;

    private final AssignationService assignationService;
    private final StudentToTeacherResourceMapper studentToTeacherResourceMapper;
    private final StudentToTestResourceMapper studentToTestResourceMapper;
    private final UserService userService;

    @PostMapping("/student-to-teacher")
    public ResponseEntity<StudentToTeacherAssignationResource> createStudentToTeacherAssign(
            @RequestBody @Valid CreateStudentToTeacherAssignation createStudentToTeacherAssignation) {
        userService.findById(createStudentToTeacherAssignation.getStudentId())
                .filter(user -> user.getAuthorities().stream().anyMatch(authority -> authority.getName() == STUDENT_AUTHORITY))
                .orElseThrow(() -> new UserNotFoundException("No found student by id: " + createStudentToTeacherAssignation.getStudentId()));
        userService.findById(createStudentToTeacherAssignation.getTeacherId())
                .filter(user -> user.getAuthorities().stream().anyMatch(authority -> authority.getName() == TEACHER_AUTHORITY))
                .orElseThrow(() -> new UserNotFoundException("No found teacher by id: " + createStudentToTeacherAssignation.getTeacherId()));
        var assignationToSave = studentToTeacherResourceMapper.toEntity(createStudentToTeacherAssignation);
        var savedAssignation = assignationService.save(assignationToSave);
        return ResponseEntity.ok(studentToTeacherResourceMapper.toResource(savedAssignation));
    }

    @PostMapping("/student-to-test")
    public ResponseEntity<StudentToTestAssignationResource> createStudentToTestAssign(
            @RequestBody @Valid CreateStudentToTestAssignation createStudentToTestAssignation) {
        userService.findById(createStudentToTestAssignation.getStudentId())
                .filter(user -> user.getAuthorities().stream().anyMatch(authority -> authority.getName() == STUDENT_AUTHORITY))
                .orElseThrow(() -> new UserNotFoundException("No found student by id: " + createStudentToTestAssignation.getStudentId()));
        var assignationToSave = studentToTestResourceMapper.toEntity(createStudentToTestAssignation);
        var savedAssignation = assignationService.save(assignationToSave);
        return ResponseEntity.ok(studentToTestResourceMapper.toResource(savedAssignation));
    }
}
