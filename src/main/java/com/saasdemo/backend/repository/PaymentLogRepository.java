package com.saasdemo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saasdemo.backend.entity.PaymentLog;

@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog,Long>{
  
    
}