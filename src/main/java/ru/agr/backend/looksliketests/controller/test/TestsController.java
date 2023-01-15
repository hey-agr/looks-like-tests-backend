package ru.agr.backend.looksliketests.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.exception.ResourceNotFoundException;
import ru.agr.backend.looksliketests.controller.resources.TestCollectionResource;
import ru.agr.backend.looksliketests.controller.resources.TestResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestValidationException;
import ru.agr.backend.looksliketests.controller.test.mapper.TestMapper;
import ru.agr.backend.looksliketests.controller.test.service.TestResourceService;
import ru.agr.backend.looksliketests.controller.test.service.TestValidationService;
import ru.agr.backend.looksliketests.controller.test.util.TestId;
import ru.agr.backend.looksliketests.service.TestService;

import jakarta.validation.Valid;


/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ApiVersion.API_V1 + "/tests")
public class TestsController {
    private final TestService testService;
    private final TestMapper testMapper;
    private final TestResourceService testResourceService;
    private final TestValidationService testValidationService;

    @GetMapping("/{testId}")
    public ResponseEntity<TestResource> getById(@TestId @PathVariable Long testId) throws ResourceNotFoundException {
        final var test = testService.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found test with id="+testId));
        return ResponseEntity.ok(testResourceService.prepareTestResource(test));
    }

    @GetMapping
    public ResponseEntity<TestCollectionResource> getAll(Pageable pageable) {
        var testsPage = testService.findPageable(pageable);
        return ResponseEntity.ok(testResourceService.prepareTestsResource(testsPage, pageable));
    }

    @PostMapping
    public ResponseEntity<TestResource> create(@RequestBody @Valid CreateTestDto createTestDto) throws TestValidationException,ResourceNotFoundException {
        testValidationService.validate(createTestDto);
        final var savedTest = testService.save(testMapper.toEntity(createTestDto));
        return getById(savedTest.getId());
    }
}
