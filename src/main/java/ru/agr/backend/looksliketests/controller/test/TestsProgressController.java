package ru.agr.backend.looksliketests.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.exception.ResourceNotFoundException;
import ru.agr.backend.looksliketests.controller.resources.TestProgressResource;
import ru.agr.backend.looksliketests.controller.test.service.TestProgressResourceService;
import ru.agr.backend.looksliketests.service.TestProgressService;
import ru.agr.backend.looksliketests.service.TestService;
import ru.agr.backend.looksliketests.service.auth.UserService;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ApiVersion.API_V1 + "/tests/progress")
public class TestsProgressController {
    private final TestProgressService testProgressService;
    private final TestProgressResourceService testProgressResourceService;
    private final TestService testService;
    private final UserService userService;

    @PostMapping("/{testId}")
    public ResponseEntity<TestProgressResource> create(@PathVariable Long testId,
                                                       @AuthenticationPrincipal UserDetails userDetails) throws ResourceNotFoundException {
        final var test = testService.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found test with id="+testId));
        final var user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        final var testProgress = testProgressService.save(
                testProgressResourceService.prepareTestProgress(test, user)
        );
        return ResponseEntity.ok(
                testProgressResourceService.prepareTestProgressResource(testProgress)
        );
    }
}
