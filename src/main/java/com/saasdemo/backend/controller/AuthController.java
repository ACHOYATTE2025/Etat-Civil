package com.saasdemo.backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saasdemo.backend.dto.SignupRequest;
import com.saasdemo.backend.dto.SignupResponse;
import com.saasdemo.backend.service.AuthService;

import jakarta.validation.Valid;



@RestController
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
    
  }

  //enregistrer une commune et l'Admin
  @PostMapping("/signup")
  public SignupResponse register(@Valid @RequestBody SignupRequest request) {
       return this.authService.Register(request);
     
  }
  
  //activer le compte de l'Admin
 
  @PreAuthorize("hasAnyAuthority('ADMIN')" )
  @PostMapping("/activationAdminCommune")
  public ResponseEntity<?> postMethodName(@RequestBody Map<String,String> activationCompteAdmin) {
      return this.authService.activationAdmin(activationCompteAdmin);
       }
  
    
}