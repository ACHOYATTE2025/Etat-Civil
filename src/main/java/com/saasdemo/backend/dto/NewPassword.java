package com.saasdemo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewPassword {
  @NotBlank
  private String email;

  @NotBlank
  private String code;

  @NotBlank
  private String password;


    
}