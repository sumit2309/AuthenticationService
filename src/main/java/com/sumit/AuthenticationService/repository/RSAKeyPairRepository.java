package com.sumit.AuthenticationService.repository;

import com.sumit.AuthenticationService.entity.RSAKeyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RSAKeyPairRepository extends JpaRepository<RSAKeyPair, Long> {
}

