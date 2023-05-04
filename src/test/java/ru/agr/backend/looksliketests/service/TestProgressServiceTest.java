package ru.agr.backend.looksliketests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agr.backend.looksliketests.db.entity.auth.User;
import ru.agr.backend.looksliketests.db.entity.main.TestProgress;
import ru.agr.backend.looksliketests.db.repository.TestProgressRepository;
import ru.agr.backend.looksliketests.service.impl.TestProgressServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Arslan Rabadanov
 */
@ExtendWith(MockitoExtension.class)
class TestProgressServiceTest {
    private static final Long TEST_PROGRESS_ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "JD@email.com";
    private static final Long USER_ID = 2L;
    @Mock
    private TestProgressRepository testProgressRepository;

    @InjectMocks
    private TestProgressServiceImpl service;

    private User givenUser;
    private ru.agr.backend.looksliketests.db.entity.main.Test givenTest;

    @BeforeEach
    void setUp() {
        givenUser = User.builder()
                .id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .activated(true)
                .build();

        givenTest = ru.agr.backend.looksliketests.db.entity.main.Test.builder()
                .attempts(2L)
                .name("Some test")
                .description("Some test description")
                .duration(3600L)
                .minCorrectAnswers(2L)
                .needVerification(false)
                .build();
    }

    @Test
    void save() {
        var expectedTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .user(givenUser)
                .test(givenTest)
                .dateStarted(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .dateFinished(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .build();

        when(testProgressRepository.save(expectedTestProgress))
                .thenReturn(expectedTestProgress);

        assertEquals(expectedTestProgress, service.save(expectedTestProgress));
    }

    @Test
    void findById() {
        var expectedTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .user(givenUser)
                .test(givenTest)
                .dateStarted(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .dateFinished(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .build();

        when(testProgressRepository.findById(TEST_PROGRESS_ID))
                .thenReturn(Optional.of(expectedTestProgress));

        var actualTestProgress = service.findById(TEST_PROGRESS_ID);

        assertTrue(actualTestProgress.isPresent());
        assertEquals(expectedTestProgress, actualTestProgress.get());
    }

    @Test
    void findByIdNotFound() {
        when(testProgressRepository.findById(TEST_PROGRESS_ID))
                .thenReturn(Optional.empty());

        var actualTestProgress = service.findById(TEST_PROGRESS_ID);

        assertFalse(actualTestProgress.isPresent());
    }

    @Test
    void findByIdNPE() {
        assertThrows(NullPointerException.class,
                () -> service.findById(null));
    }

    @Test
    void findAllByUserIdAndTestIds() {
        var expectedTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .user(givenUser)
                .test(givenTest)
                .dateStarted(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .dateFinished(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .build();

        when(testProgressRepository.findAllByUserIdAndTestIds(givenUser.getId(), new Long[]{givenTest.getId()}))
                .thenReturn(List.of(expectedTestProgress));

        var actualTestProgress = service.findAllByUserIdAndTestIds(givenUser.getId(), givenTest.getId());
        assertEquals(List.of(expectedTestProgress), actualTestProgress);
    }

    @Test
    void findAllByUserIdAndTestIdsNPE() {
        assertThrows(NullPointerException.class,
                () -> service.findAllByUserIdAndTestIds(null, null));
        final var userId = givenUser.getId();
        assertThrows(NullPointerException.class,
                () -> service.findAllByUserIdAndTestIds(userId, null));
    }

    @Test
    void finishProgress() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .test(givenTest)
                .user(givenUser)
                .dateStarted(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .build();

        when(testProgressRepository.save(givenTestProgress))
                .thenReturn(givenTestProgress);

        var result = service.finishProgress(givenTestProgress);
        assertNotNull(result.getDateFinished());
    }

    @Test
    void finishProgressNPE() {
        assertThrows(NullPointerException.class,
                () -> service.finishProgress(null));
    }

    @Test
    void isFinishedTrue() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .test(givenTest)
                .user(givenUser)
                .dateStarted(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .dateFinished(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .build();

        assertTrue(service.isFinished(givenTestProgress));
    }

    @Test
    void isFinishedFalse() {
        var givenTestProgress = TestProgress.builder()
                .id(TEST_PROGRESS_ID)
                .test(givenTest)
                .user(givenUser)
                .dateStarted(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .build();

        assertFalse(service.isFinished(givenTestProgress));
    }

    @Test
    void isFinishedNPE() {
        assertThrows(NullPointerException.class,
                () -> service.isFinished(null));
    }
}