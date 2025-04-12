package com.saasdemo.backend.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.saasdemo.backend.entity.Jwt;
import com.saasdemo.backend.entity.Utilisateur;
import com.saasdemo.backend.repository.JwtRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

@Component
public class JwtUtil {

private final JwtRepository jwtRepository;


public JwtUtil(JwtRepository jwtRepository){
  this.jwtRepository= jwtRepository;
 
}
  
  @Value("${jwt.key}")
  private  static String jwtSecret;

  @Value("${jwt.expiration}")
  private Long jwtexpiration;

  public String generateToken( Utilisateur util){
   String jwtbearer = Jwts.builder()
          .setSubject(util.getEmail())
          .claim("role",util.getRole())
          .claim("communeId",util.getCommune().getId())
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis()+jwtexpiration))
          .signWith(SignatureAlgorithm.HS256,jwtSecret)
          .compact();


       Jwt jwtbuild =
          Jwt.builder()
                  .valeur(jwtbearer.substring(0,jwtbearer.length()-1))
                  .desactive(false)
                  .expiration(false)
                  .utilisateur(util)
                  .build();
  this.jwtRepository.save(jwtbuild);

  return jwtbearer;
  }


  //methods claims
  public Claims extractAllClaims(String token) {
    return Jwts.parser()
    .setSigningKey(this.jwtSecret)
    .build()
    .parseClaimsJws(token)
    .getBody();
}

    
}