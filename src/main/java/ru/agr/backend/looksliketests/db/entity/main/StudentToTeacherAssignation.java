package ru.agr.backend.looksliketests.db.entity.main;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * @author Arslan Rabadanov
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@Entity
@Table(name = "student_to_teacher_assignation", schema = "main")
public class StudentToTeacherAssignation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @CreatedDate
    @Column(name = "date_created", nullable = false, insertable = false)
    private LocalDateTime dateCreated;
}
