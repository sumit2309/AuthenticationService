package com.sumit.AuthenticationService.entity;

import jakarta.persistence.*;

@Entity
public class RSAKeyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Use @Lob for large objects like Base64 encoded keys
    @Column(columnDefinition = "CLOB")
    private String publicKeyBase64;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String privateKeyBase64;

    // Constructors, getters, and setters

    public RSAKeyPair() {}

    public RSAKeyPair(String publicKeyBase64, String privateKeyBase64) {
        this.publicKeyBase64 = publicKeyBase64;
        this.privateKeyBase64 = privateKeyBase64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicKeyBase64() {
        return publicKeyBase64;
    }

    public void setPublicKeyBase64(String publicKeyBase64) {
        this.publicKeyBase64 = publicKeyBase64;
    }

    public String getPrivateKeyBase64() {
        return privateKeyBase64;
    }

    public void setPrivateKeyBase64(String privateKeyBase64) {
        this.privateKeyBase64 = privateKeyBase64;
    }
}


