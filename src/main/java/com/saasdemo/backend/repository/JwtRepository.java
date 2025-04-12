package com.saasdemo.backend.repository;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.saasdemo.backend.entity.Jwt;

@Repository
public interface JwtRepository extends CrudRepository<Jwt, Long> {
    Optional<Jwt> findByValeur(String valeur);

    Optional<Jwt> tokendanslaBD = Optional.empty();

    void deleteAllByExpirationAndDesactive( Boolean desactive,Boolean expire);
    void deleteByValeur(String valeur);

    @Query("FROM Jwt j WHERE  j.utilisateur.email=:email and j.desactive= :expire and j.expiration=: expire" )
    Optional <Jwt> findBytoken(String email, Boolean desactive,Boolean expire);

    @Query("FROM Jwt j WHERE  j.utilisateur.email=:email")
    Stream<Jwt> findByAbonne(String email);


    void deleteAllByValeur(String valeur);

}
