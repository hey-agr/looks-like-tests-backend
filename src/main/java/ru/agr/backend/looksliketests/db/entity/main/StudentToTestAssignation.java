package ru.agr.backend.looksliketests.db.entity.main;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * @author Arslan Rabadanov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "student_to_test_assignation", schema = "main")
public class StudentToTestAssignation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "test_id", nullable = false)
    private Long testId;

    @CreatedDate
    @Column(name = "date_created", nullable = false, insertable = false)
    private LocalDateTime dateCreated;
}
