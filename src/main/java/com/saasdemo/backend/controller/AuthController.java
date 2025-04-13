package com.saasdemo.backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saasdemo.backend.dto.CreateUserRequest;
import com.saasdemo.backend.dto.LoginAdmin;
import com.saasdemo.backend.dto.SignupRequest;
import com.saasdemo.backend.dto.SignupResponse;
import com.saasdemo.backend.service.AuthService;
import com.saasdemo.backend.service.UserService;

import jakarta.validation.Valid;



@RestController
public class AuthController {
  private final AuthService authService;
  private final UserService userService;
  public AuthController(AuthService authService,UserService userService) {
    this.authService = authService;
    this.userService = userService;
    
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

   
//Login des Admins
@PostMapping("/loginAdmin")
public SignupResponse loginAdmin(@Valid @RequestBody LoginAdmin loginAdmin){
   return this.authService.loginAdminService(loginAdmin);
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
    public ResponseEntity<?> createUser(@Validated @RequestBody CreateUserRequest request) {
       this.userService.createUser(request);
        return ResponseEntity.ok().body("L'USER "+request.getFullName()+" EST CREE");
    }


    
} 