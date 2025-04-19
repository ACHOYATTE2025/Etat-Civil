package com.saasdemo.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.saasdemo.backend.dto.ActiveAdmin;
import com.saasdemo.backend.dto.CreateUserRequest;
import com.saasdemo.backend.dto.LoginAdmin;
import com.saasdemo.backend.dto.NewPassword;
import com.saasdemo.backend.dto.ReactivedCompte;
import com.saasdemo.backend.dto.SignupRequest;
import com.saasdemo.backend.dto.SignupResponse;
import com.saasdemo.backend.dto.UserResponse;
import com.saasdemo.backend.service.AuthService;
import com.saasdemo.backend.service.UserService;
import com.saasdemo.backend.util.JwtUtil;

import jakarta.validation.Valid;




@RestController
public class AuthController {
  private final AuthService authService;
  private final UserService userService;
  private final JwtUtil jwtUtil;

  public AuthController(AuthService authService,UserService userService, JwtUtil jwtUtil) {
    this.authService = authService;
    this.userService = userService;
    this.jwtUtil = jwtUtil;
    
  }

  //enregistrer une commune et l'Admin
  @PostMapping("/signup")
  public SignupResponse register(@Valid @RequestBody SignupRequest request) {
       return this.authService.Register(request);
     
  }
  
  //activer le compte de l'Admin
 
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/activationCompte")
  public ResponseEntity<?> postMethodName(@RequestBody ActiveAdmin activationCompteAdmin) {
      return this.authService.activationAdmin(activationCompteAdmin);
       }

  
//Login des Admins
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/login")
public SignupResponse loginAdmin(@Valid @RequestBody LoginAdmin loginAdmin){
   return this.authService.loginAdminService(loginAdmin);
}


 //renvoi code d'activation
 //@PreAuthorize("hasAnyAuthority('ADMIN')" )
 @PostMapping(path = "/reactiveCompte")
 public ResponseEntity<?> reactivationCompte(@RequestBody ReactivedCompte reactived) throws Exception {
    return this.authService.renvoiCode(reactived);
 }
  
 // creation d'un user par un Admin
 @PostMapping("/createUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Validated @RequestBody CreateUserRequest request) {
       this.userService.createUser(request);
        return ResponseEntity.ok().body("L'USER "+request.getFullName()+" EST CREE");
    }


// obtenir l'utilisateur connceté
@PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
 @GetMapping("/currentUser")
public UserResponse getMethodName() {
   return this.userService.getCurrentUser();
}

//modifier mot de passe
    @ResponseStatus(value = HttpStatus.FOUND)
    @PostMapping(path = "resetPassword")
    public void ModifierMotDePasse(@RequestBody ReactivedCompte UpdateMotDePasse) throws Throwable {
        this.authService.resetpassword(UpdateMotDePasse);
    }

 //nouveau mot de passe
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "newPassword")
    public void newpassword(@RequestBody NewPassword NouveauMotDePasse) throws Throwable {
        this.authService.newpassword(NouveauMotDePasse);
    }



//deconnexion
@PostMapping(path = "deconnexion")
public ResponseEntity<?> deconex()  {
   return this.jwtUtil.deconex();
}


//desactiver un souscripteur
@PostMapping("desactiverUSER")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?>   deletesouscripteur(@RequestBody ReactivedCompte emailSouscripteur) throws Exception {
           return this.authService.deletesouscripteur( emailSouscripteur);}

    
} 