package com.saasdemo.backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saasdemo.backend.dto.CreateUserRequest;
import com.saasdemo.backend.dto.SignupRequest;
import com.saasdemo.backend.dto.SignupResponse;
import com.saasdemo.backend.dto.UserResponse;
import com.saasdemo.backend.entity.Utilisateur;
import com.saasdemo.backend.service.AuthService;
import com.saasdemo.backend.service.UserService;

import jakarta.validation.Valid;



@RestController
public class AuthController {
  private final AuthService authService;
  private final UserService userService;
  private final UserResponse userResponse;

  public AuthController(AuthService authService,UserService userService, UserResponse userResponse) {
    this.authService = authService;
    this.userService = userService;
    this.userResponse = userResponse;
    
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

 //renvoi code d'activation
 //@PreAuthorize("hasAnyAuthority('ADMIN')" )
 @PostMapping(path = "/adminCompteReactived")
 public ResponseEntity<?> reactivationCompte(@RequestBody Map<String,String> reactived) throws Exception {
    return this.authService.renvoiCode(reactived);
 }
  
 // creation d'un user par un Admin
 @PostMapping("/createUser")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createUser(@Validated @RequestBody CreateUserRequest request) {
       Utilisateur  user = userService.createUser(request);
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .communeName(user.getCommune().getNameCommune())
                .build();
    }
    
}