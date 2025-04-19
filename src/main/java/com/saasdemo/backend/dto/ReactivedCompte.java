package com.saasdemo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReactivedCompte {
  @NotBlank
  private String email;
    
}