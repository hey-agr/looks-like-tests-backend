package ru.agr.backend.looksliketests.db.entity.main;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.Hibernate;
import ru.agr.backend.looksliketests.db.entity.auth.User;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * @author Arslan Rabadanov
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldNameConstants
@Entity
@Table(name = "test_progress", schema = "main")
public class TestProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date_started", nullable = false)
    private ZonedDateTime dateStarted;

    @Column(name = "date_finished")
    private ZonedDateTime dateFinished;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TestProgress that = (TestProgress) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
