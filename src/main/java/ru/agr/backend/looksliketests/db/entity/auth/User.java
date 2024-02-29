package ru.agr.backend.looksliketests.db.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "user", schema = "auth")
public class User implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "username", length = 50, unique = true)
   @NotNull
   @Size(min = 4, max = 50)
   private String username;

   @Column(name = "password", length = 100)
   @NotNull
   @Size(min = 4, max = 100)
   private String password;

   @Column(name = "first_name", length = 50)
   @Size(min = 1, max = 100)
   private String firstName;

   @Column(name = "last_name", length = 50)
   @Size(min = 1, max = 100)
   private String lastName;

   @Column(name = "middle_name", length = 50)
   @Size(min = 1, max = 100)
   private String middleName;

   @Column(name = "email", length = 50)
   @Email
   private String email;

   @Column(name = "phone", length = 50)
   private String phone;

   @JsonIgnore
   @Column(name = "activated")
   @NotNull
   private boolean activated;

   @EqualsAndHashCode.Exclude
   @ToString.Exclude
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
   private List<UserAuthority> authorities;
}
