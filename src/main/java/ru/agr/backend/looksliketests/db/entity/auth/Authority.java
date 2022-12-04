package ru.agr.backend.looksliketests.db.entity.auth;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authority", schema = "auth")
public class Authority {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @NotNull
   private Long id;

   @Column(name = "name", length = 50)
   @NotNull
   private String name;
}
