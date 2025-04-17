package com.saasdemo.backend.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "paymentlog")
public class PaymentLog {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String status;
    private String email;
    private Integer amount;
    private String channel;
    private Instant paidAt;
    private String rawResponse;

    @ManyToOne
    private Commune commune;
    
}