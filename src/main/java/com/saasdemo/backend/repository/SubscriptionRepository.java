package com.saasdemo.backend.repository;

import com.saasdemo.backend.entity.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.commune.id = :orgId AND s.status = 'active' AND s.endDate > CURRENT_TIMESTAMP")
    Optional<Subscription> findSubscriptionByCommune(Long orgId);
}
