package ru.agr.backend.looksliketests.controller.auth.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class LoginDto {
   @NotNull
   @Size(min = 1, max = 50)
   private String username;

   @NotNull
   @Size(min = 4, max = 100)
   private String password;

   private Boolean rememberMe;
}
