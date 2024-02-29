package ru.agr.backend.looksliketests.db.entity.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@IdClass(UserAuthority.UserAuthorityKey.class)
@Entity
@Table(name = "user_authority", schema = "auth")
public class UserAuthority implements Serializable {

   @Id
   @NotNull
   @Enumerated(EnumType.STRING)
   @Column(name = "name", nullable = false)
   private AuthorityName name;

   @Id
   @EqualsAndHashCode.Exclude
   @ToString.Exclude
   @ManyToOne
   @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
   private User user;

   @Builder
   @Getter
   @Setter
   @NoArgsConstructor
   @AllArgsConstructor
   public static class UserAuthorityKey implements Serializable {
      private User user;

      private AuthorityName name;
   }

   public enum AuthorityName {
      ADMIN,
      TEACHER,
      SUPERVISOR,
      STUDENT
   }
}
