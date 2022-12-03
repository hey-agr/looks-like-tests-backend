package ru.agr.backend.looksliketests.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "auth")
public class User {
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
   @Size(min = 4, max = 50)
   private String firstName;

   @Column(name = "last_name", length = 50)
   @Size(min = 4, max = 50)
   private String lastName;

   @Column(name = "email", length = 50)
   @Size(min = 4, max = 50)
   private String email;

   @JsonIgnore
   @Column(name = "activated")
   @NotNull
   private boolean activated;

   @ManyToMany
   @JoinTable(
      name = "user_authority",
      schema = "auth",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
   @BatchSize(size = 20)
   private Set<Authority> authorities = new HashSet<>();
}
