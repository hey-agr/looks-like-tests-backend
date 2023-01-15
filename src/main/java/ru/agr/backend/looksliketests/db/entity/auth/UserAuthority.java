package ru.agr.backend.looksliketests.db.entity.auth;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
   @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
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
