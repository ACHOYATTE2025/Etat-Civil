package com.saasdemo.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saasdemo.backend.entity.Utilisateur;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

  Optional<Utilisateur> findByEmail(String email);

  Utilisateur findByPassword(String password);
  Long countByCommuneId(Long orgId);
  Long countByCommuneIdAndRole(Long orgId, String role);
  



}
