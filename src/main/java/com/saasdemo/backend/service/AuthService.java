package com.saasdemo.backend.service;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saasdemo.backend.dto.LoginAdmin;
import com.saasdemo.backend.dto.SignupRequest;
import com.saasdemo.backend.dto.SignupResponse;
import com.saasdemo.backend.entity.Commune;
import com.saasdemo.backend.entity.Utilisateur;
import com.saasdemo.backend.entity.Validation;
import com.saasdemo.backend.enums.Role;
import com.saasdemo.backend.repository.CommuneRepository;
import com.saasdemo.backend.repository.UtilisateurRepository;
import com.saasdemo.backend.security.TenantContext;
import com.saasdemo.backend.util.JwtUtil;

import jakarta.transaction.Transactional;


@Service
public class AuthService {
  
  private final CommuneRepository communeRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final ValidationService validationService;
  private final  UtilisateurRepository utilisateurRepository;
  private final UtilisateurService utilisateurService;
  private  ResponseEntity reponses;
  private Utilisateur ux = null;
   private final AuthenticationManager authenticationManager;

  //Instancier 
  public AuthService(CommuneRepository communeRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil,
  ValidationService validationService,UtilisateurRepository utilisateurRepository,UtilisateurService utilisateurService, AuthenticationManager authenticationManager){
    this.communeRepository=communeRepository;
    this.passwordEncoder =passwordEncoder;
    this.jwtUtil =jwtUtil;
    this.validationService=validationService;
    this.utilisateurRepository = utilisateurRepository;
    this.utilisateurService = utilisateurService;
    this.authenticationManager = authenticationManager;
  }
    
  

//Enregistrer une Commune + son Admin
@Transactional
public SignupResponse Register( SignupRequest request){
      
    //chercher la commune
    Commune commune =  communeRepository.findByNameCommune(request.getNamecommune());
    
        if(commune==null){
          Commune communeX = this.communeRepository.save(
          Commune.builder().nameCommune(request.getNamecommune()).build());
        
          
        //implementer les données l'utilisateur
        Utilisateur utilisateur  = Utilisateur.builder()
                                  .email(request.getEmail())
                                  .username(request.getUsername())
                                  .password(passwordEncoder.encode(request.getPassword()))
                                  .role(Role.ADMIN)
                                  .commune(communeX)
                                  .active(false)
                                  .build();


        System.out.println(utilisateur);                           
        //sauvegarder les informations
      this.communeRepository.save(utilisateur);
        //envoyer le code pour activer le compte admin
        this.validationService.createCode(utilisateur);

        //creer le token après authentification
        String token = jwtUtil.generateToken(utilisateur);

        //renvoyer un Token null pour une commune déja inscrite
        return new SignupResponse(token);
        }else{   return new SignupResponse(" VOUS ÊTES DEJA INSCRIT");}
      
        
 
  }

   //Activation de compte Admin + Commune enregistré

   public ResponseEntity<?> activationAdmin(Map<String, String> activation) {
    try{Validation codex = this.validationService.getValidation(activation.get("code"));

    if (Instant.now().isAfter(codex.getExpirationCode())) {
      throw new RuntimeException("Le Code d'Activation a expirée");}

    Utilisateur subscriberActivatedorNot = this.utilisateurRepository.findByEmail(codex.getUtilisateur().getEmail()).
            orElseThrow(() -> new RuntimeException("L'Administrateur n'exite pas "));
    subscriberActivatedorNot.setActive(true);
    this.utilisateurRepository.save(subscriberActivatedorNot);
  
     this.reponses = ResponseEntity.ok().body("LE COMPTE DE "+subscriberActivatedorNot.getRole()+" "+ subscriberActivatedorNot.getUsername()+
    " EST ACTIVEE");} 
    catch(Exception e){this.reponses= ResponseEntity.badRequest().body("LE COMPTE N'A PU ÊTRE ACTIVE =>"+e.getLocalizedMessage());}
    
    return this.reponses;
   
  }


  // login Admin + Commune
  public SignupResponse loginAdminService(LoginAdmin loginAdmin) {
    String tokenX=null;
    try{
      this.ux =  (Utilisateur) this.utilisateurService.loadUserByUsername(loginAdmin.getEmail());
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                       loginAdmin.getEmail(), loginAdmin.getPassword()));
                       System.out.println("Nom Admin :"+this.ux.getUsername());
                       TenantContext.setCurrentTenant(this.ux.getId());
                       String token = this.jwtUtil.generateToken(ux);
                       tokenX = token;}
    catch(Exception e){tokenX="ADMIN NON AUTHENTIFIE=>"+e.getLocalizedMessage();}
          
    return new SignupResponse(tokenX);    
    }
             
 
  


  // renvoi de code d'activation de compte admin de commune
   public ResponseEntity<?> renvoiCode(Map<String,String> reactived) {
    Utilisateur alpha=null;
    RuntimeException repo = null;
      try{
          Utilisateur subscriber =   (Utilisateur) this.utilisateurService.loadUserByUsername(reactived.get("email"));
        alpha=subscriber;
        if(alpha.getActive()){repo = new RuntimeException("LE COMPTE DE L'ADMIN "+  alpha.getUsername()+" EST DEJA ACTIVEE");}
        this.validationService.createCode(alpha);}
      catch(Exception e){ repo = new RuntimeException("ERREUR ADMIN INCONNU");}
    return ResponseEntity.ok().body(repo.getLocalizedMessage());
     
    }



   
   
   }

  





    
