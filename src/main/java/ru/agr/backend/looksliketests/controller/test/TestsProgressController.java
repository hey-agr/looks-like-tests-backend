package ru.agr.backend.looksliketests.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.agr.backend.looksliketests.config.security.exception.UserNotFoundException;
import ru.agr.backend.looksliketests.controller.ApiVersion;
import ru.agr.backend.looksliketests.controller.exception.IncorrectTestProgressException;
import ru.agr.backend.looksliketests.controller.exception.MaxTestAttemptsExceededException;
import ru.agr.backend.looksliketests.controller.exception.ResourceNotFoundException;
import ru.agr.backend.looksliketests.controller.exception.StudentNotAssignedToTestException;
import ru.agr.backend.looksliketests.controller.resources.TestProgressResource;
import ru.agr.backend.looksliketests.controller.resources.TestResultResource;
import ru.agr.backend.looksliketests.controller.test.dto.CreateTestAnswersDto;
import ru.agr.backend.looksliketests.controller.test.exception.TestProgressValidationException;
import ru.agr.backend.looksliketests.controller.test.mapper.TestAnswerMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestProgressMapper;
import ru.agr.backend.looksliketests.controller.test.mapper.TestResultMapper;
import ru.agr.backend.looksliketests.controller.test.service.TestProgressValidationService;
import ru.agr.backend.looksliketests.controller.validation.TestId;
import ru.agr.backend.looksliketests.service.*;
import ru.agr.backend.looksliketests.service.auth.UserService;

import java.util.Collection;

import static java.util.Objects.isNull;

/**
 * @author Arslan Rabadanov
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ApiVersion.API_V1 + "/tests/")
public class TestsProgressController {
    private final TestProgressService testProgressService;
    private final TestProgressMapper testProgressMapper;
    private final TestService testService;
    private final UserService userService;
    private final AssignationService assignationService;
    private final TestAnswerService testAnswerService;
    private final TestProgressValidationService testProgressValidationService;
    private final TestAnswerMapper testAnswerMapper;
    private final TestResultService testResultService;
    private final TestResultMapper testResultMapper;

    @PostMapping("/{testId}/starts")
    public ResponseEntity<TestProgressResource> start(@TestId @PathVariable Long testId,
                                                      @AuthenticationPrincipal UserDetails userDetails)
            throws ResourceNotFoundException, MaxTestAttemptsExceededException, StudentNotAssignedToTestException {
        final var user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);
        final var test = testService.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found test with id="+testId));
        if (!assignationService.isStudentAssignedToTest(user.getId(), testId)) {
            throw new StudentNotAssignedToTestException(userDetails.getUsername(), testId.toString());
        }
        final var attempts = testProgressService.findAllByUserIdAndTestIds(user.getId(), testId).size();
        if (attempts >= test.getAttempts()) {
            throw new MaxTestAttemptsExceededException("Maximum test attempts exceeded for the user");
        }
        final var testProgress = testProgressService.save(
                testProgressMapper.toEntity(test, user)
        );
        testResultService.save(
                testResultService.calculateTestResult(testProgress)
        );
        return ResponseEntity.ok(
                testProgressMapper.toResource(testProgress)
        );
    }

    @PostMapping("/progress/{testProgressId}/finishes")
    public ResponseEntity<TestResultResource> finish(@PathVariable Long testProgressId,
                                                     @RequestBody CreateTestAnswersDto createTestAnswersDto,
                                                     @AuthenticationPrincipal UserDetails userDetails)
            throws ResourceNotFoundException, IncorrectTestProgressException, TestProgressValidationException {
        final var user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);
        final var testProgress = testProgressService.findById(testProgressId)
                .orElseThrow(() -> new ResourceNotFoundException("Test progress with id =" +testProgressId+" is not found!"));
        if (isNull(testProgress.getUser())
                || !testProgress.getUser().getId().equals(user.getId())) {
            throw new IncorrectTestProgressException("Test progress with id: " + testProgressId + " is not started by user with id: " + user.getId());
        }
        testProgressValidationService.validate(testProgress, createTestAnswersDto);
        final var resultAnswers = createTestAnswersDto.getAnswers().stream()
                .map(currentAnswer -> testAnswerMapper.toEntity(testProgress, currentAnswer))
                .flatMap(Collection::stream)
                .toList();
        testAnswerService.saveAll(resultAnswers);

        final var finishedTestProgress = testProgressService.finishProgress(testProgress);
        var testResult = testResultService.save(
                testResultService.calculateTestResult(finishedTestProgress)
        );

        return ResponseEntity.ok(testResultMapper.toResource(testResult));
    }
}
