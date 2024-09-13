package com.sumit.AuthenticationService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelthController {

    @GetMapping("/health")
    public String healthCheck(){
        return "OK";
    }

}
