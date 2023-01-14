package ru.agr.backend.looksliketests.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.backend.looksliketests.config.security.exception.UserNotFoundException;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.resources.StudentTestAssignationCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.StudentTestHistoryCollectionResource;
import ru.agr.backend.looksliketests.controller.test.service.TestResourceService;
import ru.agr.backend.looksliketests.service.StudentAssignedTestService;
import ru.agr.backend.looksliketests.service.StudentTestHistoryService;
import ru.agr.backend.looksliketests.service.auth.UserService;
import ru.agr.backend.looksliketests.service.filter.StudentAssignedTestFilter;
import ru.agr.backend.looksliketests.service.filter.StudentTestHistoryFilter;

import java.util.Collections;
import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ApiVersion.API_V1 + "/students")
public class StudentAssignedTestsController {
    private final StudentTestHistoryService studentTestHistoryService;
    private final TestResourceService testResourceService;
    private final UserService userService;
    private final StudentAssignedTestService studentAssignedTestService;

    @GetMapping("/tests/assignations")
    public ResponseEntity<StudentTestAssignationCollectionResource> getAssignations(Pageable pageable,
                                                                                    @RequestParam(required = false) Boolean isActual,
                                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        final var user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        var filter = StudentAssignedTestFilter.builder()
                .isActual(isActual)
                .studentIds(List.of(user.getId()))
                .build();
        var testsPage = studentAssignedTestService.findFiltered(filter, pageable);
        return ResponseEntity.ok(testResourceService.prepareStudentTestAssignationsResource(user, testsPage, pageable));
    }

    @GetMapping("/tests/results")
    public ResponseEntity<StudentTestHistoryCollectionResource> getResults(Pageable pageable,
                                                                           @AuthenticationPrincipal UserDetails userDetails) {
        final var user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        final var filter = StudentTestHistoryFilter.builder()
                .studentIds(Collections.singletonList(user.getId()))
                .build();
        final var testsHistotyPage = studentTestHistoryService.findFiltered(filter, pageable);
        return ResponseEntity.ok(testResourceService.prepareStudentTestHistoryCollectionResource(testsHistotyPage, pageable));
    }
}
