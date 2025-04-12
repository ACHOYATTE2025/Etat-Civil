package com.saasdemo.backend.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "jwt")
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valeur;
    private Boolean desactive=false;
    private Boolean expiration=false;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Utilisateur utilisateur;
}
