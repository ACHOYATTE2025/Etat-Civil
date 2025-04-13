package com.saasdemo.backend.service;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saasdemo.backend.dto.SignupRequest;
import com.saasdemo.backend.dto.SignupResponse;
import com.saasdemo.backend.entity.Commune;
import com.saasdemo.backend.entity.Utilisateur;
import com.saasdemo.backend.entity.Validation;
import com.saasdemo.backend.enums.Role;
import com.saasdemo.backend.repository.CommuneRepository;
import com.saasdemo.backend.repository.UtilisateurRepository;
import com.saasdemo.backend.util.JwtUtil;

import jakarta.transaction.Transactional;


@Service
public class AuthService {
  
  private final CommuneRepository communeRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final ValidationService validationService;
  private final  UtilisateurRepository utilisateurRepository;
  private  ResponseEntity reponses;


  //Instancier 
  public AuthService(CommuneRepository communeRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil,
  ValidationService validationService,UtilisateurRepository utilisateurRepository){
    this.communeRepository=communeRepository;
    this.passwordEncoder =passwordEncoder;
    this.jwtUtil =jwtUtil;
    this.validationService=validationService;
    this.utilisateurRepository = utilisateurRepository;
  }
    
  

//authentifier un utilisateur
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

   //Activation de compte enregistré

   public ResponseEntity<?> activationAdmin(Map<String, String> activation) {
    try{Validation codex = this.validationService.getValidation(activation.get("code"));

    if (Instant.now().isAfter(codex.getExpirationCode())) {
      throw new RuntimeException("Le Code d'Activation a expirée");}

    Utilisateur subscriberActivatedorNot = this.utilisateurRepository.findByEmail(codex.getUtilisateur().getEmail()).
            orElseThrow(() -> new RuntimeException("L'Administrateur n'exite pas "));
    subscriberActivatedorNot.setActive(true);
    this.utilisateurRepository.save(subscriberActivatedorNot);
  
     this.reponses = ResponseEntity.ok().body("LE COMPTE DE L'ADMINISTRATEUR "+ subscriberActivatedorNot.getUsername()+
    " EST ACTIVEE");} 
    catch(Exception e){this.reponses= ResponseEntity.badRequest().body("LE COMPTE DE L'ADMINISTRATEUR N'A PU ÊTRE ACTIVE =>"+e.getLocalizedMessage());}
    
    return this.reponses;
   
  }

  }





    
