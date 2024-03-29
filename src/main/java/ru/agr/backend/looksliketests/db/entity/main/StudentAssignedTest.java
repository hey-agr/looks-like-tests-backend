package ru.agr.backend.looksliketests.db.entity.main;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldNameConstants;

/**
 * @author Arslan Rabadanov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldNameConstants
@Entity
@Table(name = "student_assigned_tests", schema = "main")
public class StudentAssignedTest {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "test_id")
    private Long testId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "min_correct_answers")
    private Long minCorrectAnswers;

    @Column(name = "questions_count")
    private Long questionsCount;

    @Column(name = "attempts")
    private Long attempts;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "need_verification")
    private Boolean needVerification;

    @Column(name = "attempts_count")
    private Long attemptsCount;

    @Column(name = "in_progress_count")
    private Long inProgressCount;

    @Column(name = "passed_count")
    private Long passedCount;

    @Column(name = "pending_count")
    private Long pendingCount;

    @Column(name = "failed_count")
    private Long failedCount;
}
