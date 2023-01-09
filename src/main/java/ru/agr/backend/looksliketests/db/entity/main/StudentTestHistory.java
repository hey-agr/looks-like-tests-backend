package ru.agr.backend.looksliketests.db.entity.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

/**
 * @author Arslan Rabadanov
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@FieldNameConstants
@Entity
@Table(name = "student_tests_history", schema = "main")
public class StudentTestHistory {
    @Id
    @Column(name = "test_progress_id")
    private Long testProgressId;

    @Column(name = "test_id")
    private Long testId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "question_count")
    private Long questionCount;

    @Column(name = "right_answers_count")
    private Long rightAnswersCount;

    @Column(name = "pending_answers_count")
    private Long pendingAnswersCount;

    @Column(name = "wrong_answers_count")
    private Long wrongAnswersCount;

    @Column(name = "date_started", nullable = false)
    private ZonedDateTime dateStarted;

    @Column(name = "date_finished")
    private ZonedDateTime dateFinished;

    @Column(name = "test_result_status")
    private String testResultStatus;
}
