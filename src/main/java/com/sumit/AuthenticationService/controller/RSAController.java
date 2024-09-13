package com.sumit.AuthenticationService.controller;

import com.sumit.AuthenticationService.dto.RSAKeyPairResponse;
import com.sumit.AuthenticationService.service.RSAKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rsa")
public class RSAController {

    private final RSAKeyService rsaKeyService;

    @Autowired
    public RSAController(RSAKeyService rsaKeyService) {
        this.rsaKeyService = rsaKeyService;
    }

    // Generate and store a new RSA key pair, then return the ID and public key
    @PostMapping("/generate")
    public RSAKeyPairResponse generateNewKeyPair() {
        return rsaKeyService.generateAndStoreKeyPair();
    }

    // Retrieve a key pair by ID and return the ID and public key
    @GetMapping("/key/{id}")
    public Optional<RSAKeyPairResponse> getKeyPairById(@PathVariable Long id) {
        return rsaKeyService.getKeyPairById(id);
    }

    // Validate if the incoming public key matches the stored public key by ID
    @PostMapping("/validate/{id}")
    public boolean validatePublicKey(@PathVariable Long id, @RequestBody String publicKeyBase64) {
        return rsaKeyService.isMatchingPublicKey(id, publicKeyBase64);
    }
}



