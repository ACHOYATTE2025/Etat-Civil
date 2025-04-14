package com.saasdemo.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.saasdemo.backend.repository.UtilisateurRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class UtilisateurService implements UserDetailsService {

    private final UtilisateurRepository accountRepository;
   


   

    /* ************************************************************************************************ */

   

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.accountRepository.findByEmail(email).orElseThrow(() 
        -> new ResourceAccessException("UTILISATEUR NON TROUVE"));
    }

}
