package ru.agr.backend.looksliketests.db.entity.auth;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserAuthority.UserAuthorityKey.class)
@Entity
@Table(name = "user_authority", schema = "auth")
public class UserAuthority {

   @Id
   @NotNull
   @Enumerated(EnumType.STRING)
   @Column(name = "name", nullable = false)
   private AuthorityName name;

   @Id
   @EqualsAndHashCode.Exclude
   @ToString.Exclude
   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   protected User user;

   @Builder
   @Getter
   @Setter
   @NoArgsConstructor
   @AllArgsConstructor
   public static class UserAuthorityKey implements Serializable {
      protected User user;

      protected AuthorityName name;
   }

   public enum AuthorityName {
      ADMIN,
      TEACHER,
      SUPERVISOR,
      STUDENT
   }
}
