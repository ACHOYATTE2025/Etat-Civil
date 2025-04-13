package com.saasdemo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginAdmin {
  @NotBlank
  private String email;

  @NotBlank
  private String password;
    
}