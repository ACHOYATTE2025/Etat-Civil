package com.saasdemo.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.saasdemo.backend.entity.Commune;
import com.saasdemo.backend.entity.Utilisateur;

@Repository
public interface CommuneRepository extends CrudRepository<Commune, Long> {

  Commune findByNameCommune(String nameCommune);

  void save(Utilisateur utilisateur);

 
    
}
