package ru.agr.backend.looksliketests.db.entity.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Arslan Rabadanov
 */
@Getter
@Setter
@NoArgsConstructor
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
