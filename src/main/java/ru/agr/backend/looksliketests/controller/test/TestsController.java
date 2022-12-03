package ru.agr.backend.looksliketests.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.exception.ResourceNotFoundException;
import ru.agr.backend.looksliketests.service.TestService;
import ru.agr.backend.looksliketests.controller.test.mapper.TestResourceMapper;


/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.API_V1 + "/tests")
public class TestsController {
    private final TestService testService;
    private final TestResourceMapper testResourceMapper;

    @GetMapping("/{testId}")
    public ResponseEntity<TestResource> getById(@PathVariable Long testId)
            throws ResourceNotFoundException {
        final var test = testService.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found test with id="+testId));
        return ResponseEntity.ok(
                testResourceMapper.toResource(test)
        );
    }
}
