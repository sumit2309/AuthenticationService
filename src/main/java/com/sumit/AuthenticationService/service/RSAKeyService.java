package com.sumit.AuthenticationService.service;

import com.sumit.AuthenticationService.dto.RSAKeyPairResponse;
import com.sumit.AuthenticationService.entity.RSAKeyPair;
import com.sumit.AuthenticationService.repository.RSAKeyPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

@Service
public class RSAKeyService {

    private final RSAKeyPairRepository rsaKeyPairRepository;

    @Autowired
    public RSAKeyService(RSAKeyPairRepository rsaKeyPairRepository) {
        this.rsaKeyPairRepository = rsaKeyPairRepository;
    }

    // Generate a new RSA key pair, store it in the database, and return the response DTO
    public RSAKeyPairResponse generateAndStoreKeyPair() {
        try {
            // Generate the RSA key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Encode the keys to Base64
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            // Create the RSAKeyPair entity
            RSAKeyPair rsaKeyPair = new RSAKeyPair(publicKeyBase64, privateKeyBase64);

            // **Save the entity to the database**
            RSAKeyPair savedKeyPair = rsaKeyPairRepository.save(rsaKeyPair);  // Saving to the database

            // Return the response DTO containing only the ID and public key
            return new RSAKeyPairResponse(savedKeyPair.getId(), savedKeyPair.getPublicKeyBase64());
        } catch (Exception e) {
            throw new RuntimeException("Error generating RSA keys", e);
        }
    }

    // Retrieve a stored RSA key pair by ID and return the response DTO
    public Optional<RSAKeyPairResponse> getKeyPairById(Long id) {
        return rsaKeyPairRepository.findById(id)
                .map(keyPair -> new RSAKeyPairResponse(keyPair.getId(), keyPair.getPublicKeyBase64()));
    }

    // Validate if the incoming public key matches the stored public key by ID
    public boolean isMatchingPublicKey(Long id, String publicKeyBase64) {
        Optional<RSAKeyPair> keyPairOptional = rsaKeyPairRepository.findById(id);
        if (keyPairOptional.isEmpty()) {
            return false;
        }

        RSAKeyPair keyPair = keyPairOptional.get();

        try {
            byte[] decodedPublicKey = Base64.getDecoder().decode(publicKeyBase64);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey incomingPublicKey = keyFactory.generatePublic(keySpec);

            byte[] originalPublicKey = Base64.getDecoder().decode(keyPair.getPublicKeyBase64());
            X509EncodedKeySpec originalKeySpec = new X509EncodedKeySpec(originalPublicKey);
            PublicKey originalPublicKeyObj = keyFactory.generatePublic(originalKeySpec);

            return incomingPublicKey.equals(originalPublicKeyObj);
        } catch (Exception e) {
            return false;
        }
    }
}

